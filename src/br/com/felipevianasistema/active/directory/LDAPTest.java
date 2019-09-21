
package br.com.felipevianasistema.active.directory;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author Felipe Viana
 */
public class LDAPTest {

  private static final String ATTRIBUTE_FOR_USER = "sAMAccountName";

        public Attributes autenticacaoDoUsuario(String username, String password, String _domain, String host, String dn) throws NamingException {

            // todos os atributos que serão retornados, precisam ser adicionados aqui.
            String returnedAtts[] = {"cn", "givenName", "mail", "memberOf"};
            String searchFilter = "(&(objectClass=user)(" + ATTRIBUTE_FOR_USER + "=" + username + "))";
            

            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);

            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchBase = dn;
            Hashtable environment = new Hashtable();
            environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
      
            environment.put(Context.PROVIDER_URL, "ldap://" + host + ":389");
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            
            environment.put(Context.SECURITY_PRINCIPAL, username + "@" + _domain);
            environment.put(Context.SECURITY_CREDENTIALS, password);
            LdapContext ctxGC = null;
            try {
                ctxGC = new InitialLdapContext(environment, null);

                NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
                while (answer.hasMoreElements()) {
                    SearchResult sr = (SearchResult) answer.next();
                    Attributes attrs = sr.getAttributes();
                    if (attrs != null) {
                        return attrs;
                    }
                }

            } catch (NamingException e) {
                throw e;
            }
            return null;
        }
    }