package com.example.demo.file;

import java.util.List;

import org.demoiselle.signer.core.extension.DefaultExtension;
import org.demoiselle.signer.core.extension.DefaultExtensionType;
import org.demoiselle.signer.core.extension.ICPBrasilExtension;
import org.demoiselle.signer.core.extension.ICPBrasilExtensionType;

public class Cert {
    @ICPBrasilExtension(type=ICPBrasilExtensionType.CPF)
    private String cpf;

    @ICPBrasilExtension(type=ICPBrasilExtensionType.NAME)
    private String nome;

    @DefaultExtension(type=DefaultExtensionType.CRL_URL)
    private List<String> crlURL;

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
       return nome;
    }

    public List<String> getCrlURL() {
        return crlURL;
    }

}
