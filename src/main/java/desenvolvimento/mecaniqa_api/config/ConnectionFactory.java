package desenvolvimento.mecaniqa_api.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Abre conexões JDBC nativas com o MySQL (sem ORM, sem JPA). */
@Component
public class ConnectionFactory {

    private final String url;
    private final String usuario;
    private final String senha;

    public ConnectionFactory(
            @Value("${mecaniqa.db.url}") String url,
            @Value("${mecaniqa.db.user}") String usuario,
            @Value("${mecaniqa.db.password}") String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }
}
