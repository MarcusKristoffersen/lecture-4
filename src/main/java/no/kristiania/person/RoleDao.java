package no.kristiania.person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {

    private DataSource dataSource;

    public RoleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(String roleName) {

    }

    public List<String> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from roles")) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {

                }
                    ArrayList<String> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(rs.getString("name"));
                    }

                    return result;
                }
             }
        }
    }
}
