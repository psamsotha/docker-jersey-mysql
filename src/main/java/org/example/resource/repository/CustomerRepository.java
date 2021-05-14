package org.example.resource.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.example.resource.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    private final DataSource dataSource;

    @Inject
    public CustomerRepository(DataSource dataSource) {
        Objects.requireNonNull(dataSource, "Datasource must not be null.");
        this.dataSource = dataSource;
    }

    public Customer getCustomer(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Customer customer = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT * FROM CUSTOMER WHERE ID=?");
            ps.setInt(1, id);
            logger.debug("Querying: {}", ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                int cId = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                customer = new Customer();
                customer.setId(cId);
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                logger.debug("Found result: {}", customer);
            }
        }  finally {
            logger.debug("Closing database resources: {}, {}, {}", conn, ps, rs);
            if (conn != null) conn.close();
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }

        return customer;
    }
}
