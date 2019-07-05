package br.com.sistema.pmt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuarios implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Informe a identidade")
    private String identidade;

    @ManyToOne
    @org.hibernate.annotations.ForeignKey(name = "postoGraduacao_id")
    private PostoGraduacao postoGraduacao;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_role",
    	joinColumns = @JoinColumn(name = "usuario_id", 
    				  referencedColumnName = "id",
    				  table = "usuarios"),
    	inverseJoinColumns = @JoinColumn(name = "role_id",
    				  referencedColumnName = "id",
    				  table = "role"))
    private List<Role> roles;

    @NotBlank(message = "Informe o nome completo")
    private String nomeCompleto;

    @NotBlank(message = "Informe o nome de guerra")
    private String nomeDeGuerra;

    @NotBlank(message = "Informe o nome de usuário")
    private String username;

    @NotBlank(message = "Informe uma senha")
    private String password;
    
    @OneToMany(mappedBy = "usuarioRemetente")
    private List<Pedido> pedidos;

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public PostoGraduacao getPostoGraduacao() {
        return postoGraduacao;
    }

    public void setPostoGraduacao(PostoGraduacao postoGraduacao) {
        this.postoGraduacao = postoGraduacao;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto.toUpperCase();
    }

    public String getNomeDeGuerra() {
        return nomeDeGuerra;
    }

    public void setNomeDeGuerra(String nomeDeGuerra) {
        this.nomeDeGuerra = nomeDeGuerra.toUpperCase();
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
