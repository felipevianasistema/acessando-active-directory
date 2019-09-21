package br.com.felipevianasistema.active.directory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.naming.directory.Attributes;

/**
 *
 * @author Felipe Viana
 */
public class AD {

    public static void main(String[] args) throws IOException {

        try {
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            System.out.println("Informe o nome:");
            String username = in.readLine();
            System.out.println("Informe a senha:");
            String password = in.readLine();

            LDAPTest ldap = new LDAPTest();
            Attributes att = ldap.autenticacaoDoUsuario(username, password, "ad.com.br", "10.100.1.161", "DC=tech,DC=com,DC=br");

            if (att == null) {
                System.out.println("Usuário não autenticado no AD.");
            } else {
                // retorna a informação do usuário.
                System.out.println("---------------------------------");
                System.out.println("CN: " + att.get("cn").toString());
                System.out.println("Nome do usuário no AD: =" + att.get("givenName").toString());
                System.out.println("E-mail: " + att.get("mail").toString());
                System.out.println("Grupo:" + att.get("memberOf").toString());
                System.out.println("---------------------------------");
            }
        } catch (Exception ee) {
            ee.getMessage();
            ee.printStackTrace();
        }
    }
}