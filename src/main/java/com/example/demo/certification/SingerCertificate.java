package com.example.demo.certification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

import org.demoiselle.signer.core.CertificateManager;
import org.demoiselle.signer.core.extension.BasicCertificate;
import org.demoiselle.signer.core.extension.CertificateExtra;
import org.demoiselle.signer.core.repository.Configuration;
import org.demoiselle.signer.policy.impl.cades.factory.PKCS7Factory;
import org.demoiselle.signer.policy.impl.cades.pkcs7.impl.CAdESSigner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.file.Cert;

@RestController
public class SingerCertificate {
	
	
	public void getCertificate(final CertificateFactory cf,final String file) throws Exception {
		//pega um arquivo e cria um gernciador de certificado
		File certFile = new File(file);
		CertificateManager cm = new CertificateManager(certFile);
		InputStream inputStreamFile = new FileInputStream(certFile);
		
		//cria comando basico para verificar o certificado
		Cert cert = cm.load(Cert.class);	
		BasicCertificate bc = new BasicCertificate(inputStreamFile);

		System.out.println(bc.getName());
		System.out.println(bc.getEmail());
		System.out.println(bc.getSerialNumber());
		
		//transforma o certificado em certificado do padao X509 e pega informações
		X509Certificate x509Certificate =(X509Certificate)cf.generateCertificate(inputStreamFile);
		CertificateExtra ce = new CertificateExtra(x509Certificate);
		
		System.out.println(ce.getOID_2_16_76_1_3_1().getCPF());
		System.out.println(ce.getOID_2_16_76_1_3_3().getCNPJ());
		System.out.println(ce.getOID_2_16_76_1_3_2().getName());
		
		
		//cria um verificador para verificar se o certificado é um certificado válido nos padrões do ICPBrasil
		Configuration config = Configuration.getInstance();
		config.setCrlIndex(".crl_index");
		config.setCrlPath("/tmp/crls/");
		config.setOnline(false);
		
		System.out.println();
	
	}
	
	@GetMapping("/certificate")
	public byte[] assinaturaCadesDetached(@PathVariable("path")String pathFileTxt, X509Certificate[] certificates, byte[] privateKey) throws Exception {
		byte[] content = readContent(pathFileTxt);
		CAdESSigner singer = (CAdESSigner) PKCS7Factory.getInstance().factoryDefault();
		singer.setCertificates(certificates);
	
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey privKey = factory.generatePrivate(spec);
		
		singer.setPrivateKey(privKey);
		
		return singer.doDetachedSign(content);
		
	}
	
	private byte[] readContent(String pathFileTxt) throws IOException {
		  /*File file = new File(pathFileTxt);
		  byte[] bytesArray = new byte[(int) file.length()]; 
		  try {
			  FileInputStream fis = new FileInputStream(file);
			  fis.read(bytesArray); //read file into bytes[]
			  fis.close();
		  } catch(Exception e) {
			  System.err.println(e);
		  }*/	
		return Files.readAllBytes(new File(pathFileTxt).toPath());
	}

}