package br.com.bancodigital.api.async.impl;

import br.com.bancodigital.api.async.ContaAsync;
import br.com.bancodigital.api.domain.exception.PropostaNaoEncontradaException;
import br.com.bancodigital.api.domain.model.Conta;
import br.com.bancodigital.api.domain.model.Proposta;
import br.com.bancodigital.api.domain.model.enums.StatusProposta;
import br.com.bancodigital.api.domain.repository.Contas;
import br.com.bancodigital.api.domain.repository.Propostas;
import br.com.bancodigital.api.domain.util.GeradorCodigoUtil;
import br.com.bancodigital.api.email.sending.Mailer;
import br.com.bancodigital.api.email.sending.Message;
import br.com.bancodigital.api.model.AceiteModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class ContaAsyncImpl implements ContaAsync {

    @Autowired
    private Propostas propostas;

    @Autowired
    private Contas contas;

    @Autowired
    private Mailer mailer;

    @Override
    public AceiteModel verificarAceite(Long id, Boolean aceite) {
        var proposta = buscarPorId(id);
        proposta.setStatus(aceite ? StatusProposta.APROVADA : StatusProposta.NEGADA);
        propostas.save(proposta);

        var aceiteModel = toAceiteModel(proposta);

        return aceiteModel;
    }

    @Override
    public void criarConta(Long id) {
        var proposta = buscarPorId(id);

        if (proposta.getStatus().equals(StatusProposta.APROVADA)) {
            var conta = criar(proposta);

            atualizarProposta(proposta, conta);
            enviarEmail(conta);
        }
    }

    private Conta criar(Proposta proposta) {
        var agencia = GeradorCodigoUtil.gerar(4);
        var conta_numero = GeradorCodigoUtil.gerar(8);
        var conta = new Conta(agencia, conta_numero, proposta.getCliente());

        return contas.save(conta);
    }

    private void atualizarProposta(Proposta proposta, Conta conta) {
        proposta.setConta(conta);
        proposta.setStatus(StatusProposta.FINALIZADA);
        propostas.save(proposta);
    }

    private void enviarEmail(Conta conta) {
        Message message = Message.builder()
                .remetente("Nosso Banco Digital <nosso.banco.digital@gmail.com>")
                .destinatarios(Arrays.asList(conta.getCliente().getEmail()))
                .assunto("Conta Digital")
                .corpo(getCorpo(conta))
                .build();

        mailer.send(message);
    }

    private String getCorpo(Conta conta) {
        StringBuilder corpo = new StringBuilder();
        corpo.append("Olá, seja bem-vindo(a) ao Nosso Banco Digital!\n\n");
        corpo.append("Dados da sua conta:\n");
        corpo.append("Agência: " + conta.getAgencia() + "\n");
        corpo.append("Conta: " + conta.getConta() + "\n");
        corpo.append("Código do banco: " + conta.getCodigoBanco() + "\n");
        corpo.append("Tipo: " + conta.getTipo() + "\n");
        corpo.append("Saldo: " + conta.getSaldo());

        return corpo.toString();
    }

    private Proposta buscarPorId(Long id) {
        return propostas.findById(id)
                .orElseThrow(() -> new PropostaNaoEncontradaException("Proposta não localizada e/ou não foi iniciado um cadastro de cliente."));
    }

    private AceiteModel toAceiteModel(Proposta proposta) {
        var mensagem = proposta.getStatus().equals(StatusProposta.APROVADA) ?
                "Sua conta será criada e um e-mail será enviado com seus dados" : "Que pena! Entraremos em contato em breve.";

        return AceiteModel.builder()
                .mensagem(mensagem)
                .nomeCliente(proposta.getCliente().getNome())
                .emailCliente(proposta.getCliente().getEmail())
                .status(proposta.getStatus())
                .build();
    }

}
