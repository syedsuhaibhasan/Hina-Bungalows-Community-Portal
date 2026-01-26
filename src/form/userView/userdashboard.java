/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form.userView;

import dao.ConnectionProvider;
import utility.UIUtils;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;
import java.time.LocalDate;
/**
 *
 * @author 24F-CS-192
 */
public class userdashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(userdashboard.class.getName());

    /**
     * Creates new form userdashboard
     */
    
    Stack<String> Notifications = new Stack<>();
    static String userEmail = null;
    public userdashboard(String email) throws SQLException {
        userEmail = email;
        initComponents(); 
        monthComboBox.setSelectedItem(getCurrentMonthName());
        yearComboBox.setSelectedItem(String.valueOf(LocalDate.now().getYear()));
        monthComboBox.addActionListener(this::monthComboBoxActionPerformed);
        yearComboBox.addActionListener(this::yearComboBoxActionPerformed);
        loadUserInfo(userEmail);
        loadPaymentStatus();
        addElement();
    }

    private String getCurrentMonthName() {
        String[] months = {"January", "Feburary", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
        int index = LocalDate.now().getMonthValue() - 1;
        return months[index];
    }

    private void loadPaymentStatus() throws SQLException {
        String month = getSelectedMonth();
        int year = getSelectedYear();

        monthValueLabel.setText(month);
        statusValueLabel.setText("-");
        amountValueLabel.setText("-");

        Integer houseNo = parseHouseNoFromLabel();
        if (houseNo == null) {
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionProvider.getcon();
            ps = con.prepareStatement("SELECT payment_status, amount_due FROM payments WHERE house_no = ? AND month = ? AND year = ?");
            ps.setInt(1, houseNo);
            ps.setString(2, month);
            ps.setInt(3, year);
            rs = ps.executeQuery();

            if (rs.next()) {
                int status = rs.getInt("payment_status");
                statusValueLabel.setText(status == 1 ? "Paid" : "Unpaid");
                amountValueLabel.setText(String.valueOf(rs.getInt("amount_due")));
            } else {
                statusValueLabel.setText("N/A");
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    private String getSelectedMonth() {
        Object selected = monthComboBox.getSelectedItem();
        return selected.toString().trim();
    }

    private int getSelectedYear() {
        Object selected = yearComboBox.getSelectedItem();
        if (selected == null) {
            return LocalDate.now().getYear();
        }
        String value = selected.toString().trim();
        if (value.isEmpty()) {
            return LocalDate.now().getYear();
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return LocalDate.now().getYear();
        }
    }

    private Integer parseHouseNoFromLabel() {
        if (houseValueLabel == null) {
            return null;
        }
        String text = houseValueLabel.getText();
        if (text == null) {
            return null;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty() || "-".equals(trimmed)) {
            return null;
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException ex) {
            return null;
        }
    }


    private void loadUserInfo(String email) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            nameValueLabel.setText("-");
            houseValueLabel.setText("-");
            ownershipValueLabel.setText("-");
            return;
        }

        String sql = "SELECT name, house_no, ownership FROM user_data WHERE email = ?";
        try (Connection con = ConnectionProvider.getcon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nameValueLabel.setText(rs.getString("name"));
                    houseValueLabel.setText(String.valueOf(rs.getInt("house_no")));

                    String ownershipRaw = rs.getString("ownership");
                    ownershipValueLabel.setText(formatOwnership(ownershipRaw));
                } else {
                    nameValueLabel.setText("-");
                    houseValueLabel.setText("-");
                    ownershipValueLabel.setText("-");
                }
            }
        }
    }

    private String formatOwnership(String ownershipRaw) {
        if (ownershipRaw == null || ownershipRaw.trim().isEmpty()) {
            return "-";
        }
        String normalized = ownershipRaw.trim().toLowerCase();
        if ("1".equals(normalized) || "true".equals(normalized)) {
            return "Owned";
        }
        if ("0".equals(normalized) || "false".equals(normalized)) {
            return "Rented";
        }
        return ownershipRaw;
    }

    public void addElement() throws SQLException{
        String query = "SELECT notification FROM notify";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = ConnectionProvider.getcon();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            Notifications.clear();
            while(rs.next()){
                Notifications.push("From admin: " + rs.getString("notification"));
            }
            Stack<String> model = new Stack<>();
            while(!Notifications.isEmpty()){
                model.addElement(Notifications.pop());
            }
            notificationList.setListData(model);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        bodyPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        infoTitleLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        houseLabel = new javax.swing.JLabel();
        ownershipLabel = new javax.swing.JLabel();
        nameValueLabel = new javax.swing.JLabel();
        houseValueLabel = new javax.swing.JLabel();
        ownershipValueLabel = new javax.swing.JLabel();
        paymentPanel = new javax.swing.JPanel();
        paymentTitleLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        monthValueLabel = new javax.swing.JLabel();
        statusValueLabel = new javax.swing.JLabel();
        amountValueLabel = new javax.swing.JLabel();
        monthComboBox = new javax.swing.JComboBox<>();
        yearComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        notificationTitleLabel = new javax.swing.JLabel();
        notificationScrollPane = new javax.swing.JScrollPane();
        notificationList = new javax.swing.JList<>();
        actionsPanel = new javax.swing.JPanel();
        viewKeBillButton = new javax.swing.JButton();
        paymentRecbtn = new javax.swing.JButton();
        monthlyExpensebtn = new javax.swing.JButton();
        complainbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Dashboard");
        setMinimumSize(new java.awt.Dimension(900, 600));

        mainPanel.setBackground(new java.awt.Color(245, 247, 250));

        headerPanel.setBackground(new java.awt.Color(2, 100, 182));

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("USER DASHBOARD");

        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logoutButton.setText("LOGOUT");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 520, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        bodyPanel.setBackground(new java.awt.Color(245, 247, 250));

        leftPanel.setBackground(new java.awt.Color(245, 247, 250));

        infoPanel.setBackground(new java.awt.Color(255, 255, 255));
        infoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        infoTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        infoTitleLabel.setText("Resident Info");

        nameLabel.setText("Name:");

        houseLabel.setText("House No:");

        ownershipLabel.setText("Ownership:");

        nameValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nameValueLabel.setText("-");

        houseValueLabel.setText("-");

        ownershipValueLabel.setText("-");

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoTitleLabel)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(houseLabel)
                            .addComponent(ownershipLabel))
                        .addGap(16, 16, 16)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameValueLabel)
                            .addComponent(houseValueLabel)
                            .addComponent(ownershipValueLabel))))
                .addContainerGap(295, Short.MAX_VALUE))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(infoTitleLabel)
                .addGap(16, 16, 16)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameValueLabel))
                .addGap(10, 10, 10)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(houseLabel)
                    .addComponent(houseValueLabel))
                .addGap(10, 10, 10)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ownershipLabel)
                    .addComponent(ownershipValueLabel))
                .addGap(18, 18, 18))
        );

        paymentPanel.setBackground(new java.awt.Color(255, 255, 255));
        paymentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        paymentTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        paymentTitleLabel.setText("Payment Status");

        monthLabel.setText("Current Month:");

        statusLabel.setText("Status:");

        amountLabel.setText("Amount Due:");

        monthValueLabel.setText("-");

        statusValueLabel.setText("-");

        amountValueLabel.setText("-");

        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "Feburary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        yearComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2025", "2026", "2027", "2028", "2029", "2030", " " }));

        jLabel1.setText("Year");

        jLabel2.setText("Month");

        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paymentTitleLabel)
                            .addGroup(paymentPanelLayout.createSequentialGroup()
                                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(monthLabel)
                                    .addComponent(statusLabel)
                                    .addComponent(amountLabel))
                                .addGap(16, 16, 16)
                                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(monthValueLabel)
                                    .addComponent(statusValueLabel)
                                    .addComponent(amountValueLabel))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))))
        );
        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(paymentTitleLabel)
                .addGap(16, 16, 16)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel)
                    .addComponent(monthValueLabel))
                .addGap(10, 10, 10)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusLabel)
                    .addComponent(statusValueLabel))
                .addGap(10, 10, 10)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountLabel)
                    .addComponent(amountValueLabel))
                .addGap(18, 18, 18)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(paymentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(paymentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rightPanel.setBackground(new java.awt.Color(255, 255, 255));
        rightPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        notificationTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        notificationTitleLabel.setText("Notifications");

        notificationList.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        notificationList.setForeground(new java.awt.Color(2, 100, 182));
        notificationList.setFixedCellHeight(36);
        notificationScrollPane.setViewportView(notificationList);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notificationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                    .addComponent(notificationTitleLabel))
                .addGap(16, 16, 16))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(notificationTitleLabel)
                .addGap(12, 12, 12)
                .addComponent(notificationScrollPane)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        actionsPanel.setBackground(new java.awt.Color(245, 247, 250));

        viewKeBillButton.setBackground(new java.awt.Color(2, 100, 182));
        viewKeBillButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewKeBillButton.setForeground(new java.awt.Color(255, 255, 255));
        viewKeBillButton.setText("VIEW KE BILL");
        viewKeBillButton.addActionListener(this::viewKeBillButtonActionPerformed);

        paymentRecbtn.setBackground(new java.awt.Color(2, 100, 182));
        paymentRecbtn.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        paymentRecbtn.setForeground(new java.awt.Color(255, 255, 255));
        paymentRecbtn.setText("VIEW PAYMENT RECORDS");
        paymentRecbtn.addActionListener(this::paymentRecbtnActionPerformed);

        monthlyExpensebtn.setBackground(new java.awt.Color(2, 100, 182));
        monthlyExpensebtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        monthlyExpensebtn.setForeground(new java.awt.Color(255, 255, 255));
        monthlyExpensebtn.setText("Monthly Expense");
        monthlyExpensebtn.addActionListener(this::monthlyExpensebtnActionPerformed);

        complainbtn.setBackground(new java.awt.Color(2, 100, 182));
        complainbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        complainbtn.setForeground(new java.awt.Color(255, 255, 255));
        complainbtn.setText("Register Complain");

        javax.swing.GroupLayout actionsPanelLayout = new javax.swing.GroupLayout(actionsPanel);
        actionsPanel.setLayout(actionsPanelLayout);
        actionsPanelLayout.setHorizontalGroup(
            actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(viewKeBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(paymentRecbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(monthlyExpensebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(complainbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        actionsPanelLayout.setVerticalGroup(
            actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewKeBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentRecbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthlyExpensebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(complainbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(actionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void monthlyExpensebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyExpensebtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthlyExpensebtnActionPerformed

    private void viewKeBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewKeBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewKeBillButtonActionPerformed

    private void paymentRecbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentRecbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentRecbtnActionPerformed

    private void monthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            loadPaymentStatus();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            loadPaymentStatus();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        UIUtils.applyFlatLafAndRefresh();
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new userdashboard(userEmail).setVisible(true);
            } catch (SQLException ex) {
                System.getLogger(userdashboard.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsPanel;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JLabel amountValueLabel;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JButton complainbtn;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel houseLabel;
    private javax.swing.JLabel houseValueLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel infoTitleLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> monthComboBox;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JLabel monthValueLabel;
    private javax.swing.JButton monthlyExpensebtn;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameValueLabel;
    private javax.swing.JList notificationList;
    private javax.swing.JScrollPane notificationScrollPane;
    private javax.swing.JLabel notificationTitleLabel;
    private javax.swing.JLabel ownershipLabel;
    private javax.swing.JLabel ownershipValueLabel;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JButton paymentRecbtn;
    private javax.swing.JLabel paymentTitleLabel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel statusValueLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton viewKeBillButton;
    private javax.swing.JComboBox<String> yearComboBox;
    // End of variables declaration//GEN-END:variables
}
