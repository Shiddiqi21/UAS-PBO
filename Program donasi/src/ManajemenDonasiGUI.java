import javax.swing.*; // Mengimpor pustaka untuk GUI Swing
import java.awt.*; // Mengimpor pustaka untuk pengaturan layout dan komponen GUI
import java.sql.*; // Mengimpor pustaka untuk koneksi dan operasi database

public class ManajemenDonasiGUI extends JFrame { // Kelas utama yang mewarisi JFrame
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/manajemen_donasi"; // URL database PostgreSQL
    private static final String DB_USERNAME = "postgres"; // Username untuk database
    private static final String DB_PASSWORD = "Hashiddiqi.21"; // Password untuk database
    private JTextField usernameField, passwordField, captchaField; // Field input untuk username, password, dan captcha
    private JTextArea outputArea; // Area teks untuk menampilkan output
    private String captcha = "donasi2024"; // Captcha default untuk verifikasi

    public ManajemenDonasiGUI() { // Konstruktor untuk menginisialisasi GUI
        setTitle("Manajemen Donasi Al-Ikhlas"); // Mengatur judul frame
        setSize(800, 600); // Mengatur ukuran frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Menentukan aksi saat frame ditutup
        setLocationRelativeTo(null); // Menempatkan frame di tengah layar
        setLayout(new BorderLayout()); // Mengatur layout untuk frame

        JPanel loginPanel = new JPanel(); // Membuat panel untuk login
        loginPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Mengatur layout grid dengan jarak antar komponen
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Menambahkan margin pada panel

        loginPanel.add(new JLabel("Username:")); // Label untuk username
        usernameField = new JTextField(); // Input field untuk username
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:")); // Label untuk password
        passwordField = new JPasswordField(); // Input field untuk password
        loginPanel.add(passwordField);

        loginPanel.add(new JLabel("Captcha: " + captcha)); // Label untuk captcha
        captchaField = new JTextField(); // Input field untuk captcha
        loginPanel.add(captchaField);

        JButton loginButton = new JButton("Login"); // Tombol untuk login
        loginButton.addActionListener(e -> loginAction()); // Menambahkan aksi pada tombol login
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER); // Menambahkan panel login ke tengah frame
        setVisible(true); // Menampilkan frame
    }

    private void loginAction() { // Metode untuk aksi login
        String username = usernameField.getText(); // Mendapatkan input username
        String password = new String(((JPasswordField) passwordField).getPassword()); // Mendapatkan input password
        String captchaInput = captchaField.getText(); // Mendapatkan input captcha

        if (username.equals("hasbi") && password.equals("alikhlas") && captchaInput.equals(captcha)) {
            showMainMenu(); // Menampilkan menu utama jika login berhasil
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal, coba lagi."); // Pesan error jika login gagal
        }
    }

    private void showMainMenu() { // Metode untuk menampilkan menu utama
        getContentPane().removeAll(); // Menghapus semua komponen dari frame

        JPanel menuPanel = new JPanel(); // Panel untuk menu utama
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10)); // Mengatur layout grid untuk menu
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Menambahkan margin pada panel

        JButton tambahUangButton = new JButton("Tambah Donasi Uang"); // Tombol untuk tambah donasi uang
        tambahUangButton.addActionListener(e -> tambahDonasiUang()); // Menambahkan aksi pada tombol
        menuPanel.add(tambahUangButton);

        JButton tambahBarangButton = new JButton("Tambah Donasi Barang"); // Tombol untuk tambah donasi barang
        tambahBarangButton.addActionListener(e -> tambahDonasiBarang()); // Menambahkan aksi pada tombol
        menuPanel.add(tambahBarangButton);

        JButton lihatDonasiButton = new JButton("Lihat Donasi"); // Tombol untuk melihat daftar donasi
        lihatDonasiButton.addActionListener(e -> lihatDonasi()); // Menambahkan aksi pada tombol
        menuPanel.add(lihatDonasiButton);

        JButton hapusDonasiButton = new JButton("Hapus Donasi"); // Tombol untuk menghapus donasi
        hapusDonasiButton.addActionListener(e -> hapusDonasi()); // Menambahkan aksi pada tombol
        menuPanel.add(hapusDonasiButton);

        JButton ubahDonasiButton = new JButton("Ubah Donasi"); // Tombol untuk mengubah donasi
        ubahDonasiButton.addActionListener(e -> ubahDonasi()); // Menambahkan aksi pada tombol
        menuPanel.add(ubahDonasiButton);

        JButton keluarButton = new JButton("Keluar"); // Tombol untuk keluar dari aplikasi
        keluarButton.addActionListener(e -> System.exit(0)); // Menambahkan aksi keluar pada tombol
        menuPanel.add(keluarButton);

        outputArea = new JTextArea(); // Area teks untuk output
        outputArea.setEditable(false); // Menonaktifkan pengeditan
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Mengatur font teks
        JScrollPane scrollPane = new JScrollPane(outputArea); // Menambahkan scroll pada area teks

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Panel utama untuk menu dan output
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Menambahkan margin pada panel
        mainPanel.add(menuPanel, BorderLayout.WEST); // Menambahkan menu di sisi kiri
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Menambahkan area output di tengah

        add(mainPanel); // Menambahkan panel utama ke frame
        revalidate(); // Memperbarui tampilan
        repaint(); // Merender ulang tampilan
    }

    private void tambahDonasiUang() { // Metode untuk menambahkan donasi uang
        try {
            String idDonasi = JOptionPane.showInputDialog(this, "Masukkan ID Donasi"); // Input ID donasi
            if (idDonasi == null || idDonasi.trim().isEmpty()) return;

            String namaDonatur = JOptionPane.showInputDialog(this, "Masukkan Nama Donatur"); // Input nama donatur
            if (namaDonatur == null || namaDonatur.trim().isEmpty()) return;

            String jumlahStr = JOptionPane.showInputDialog(this, "Masukkan Jumlah Donasi"); // Input jumlah donasi
            if (jumlahStr == null || jumlahStr.trim().isEmpty()) return;
            double jumlahDonasi = Double.parseDouble(jumlahStr); // Mengonversi jumlah ke tipe double

            String deskripsiDonasi = JOptionPane.showInputDialog(this, "Masukkan Deskripsi Donasi"); // Input deskripsi donasi
            if (deskripsiDonasi == null) deskripsiDonasi = "";

            String tanggalStr = JOptionPane.showInputDialog(this, "Masukkan Tanggal Donasi (yyyy-MM-dd)"); // Input tanggal donasi
            if (tanggalStr == null || tanggalStr.trim().isEmpty()) return;
            java.sql.Date tanggalDonasi = java.sql.Date.valueOf(tanggalStr); // Mengonversi tanggal ke tipe Date

            simpanKeDatabaseUang(idDonasi, namaDonatur, jumlahDonasi, deskripsiDonasi, tanggalDonasi); // Simpan ke database
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format jumlah donasi tidak valid!"); // Pesan error jika jumlah tidak valid
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid! Gunakan format yyyy-MM-dd"); // Pesan error jika tanggal tidak valid
        }
    }

    private void tambahDonasiBarang() { // Metode untuk menambahkan donasi barang
        try {
            String idPemberi = JOptionPane.showInputDialog(this, "Masukkan ID Pemberi"); // Input ID pemberi
            if (idPemberi == null || idPemberi.trim().isEmpty()) return;

            String namaDonatur = JOptionPane.showInputDialog(this, "Masukkan Nama Donatur"); // Input nama donatur
            if (namaDonatur == null || namaDonatur.trim().isEmpty()) return;

            String namaBarang = JOptionPane.showInputDialog(this, "Masukkan Nama Barang"); // Input nama barang
            if (namaBarang == null || namaBarang.trim().isEmpty()) return;

            String jumlahStr = JOptionPane.showInputDialog(this, "Masukkan Jumlah Barang"); // Input jumlah barang
            if (jumlahStr == null || jumlahStr.trim().isEmpty()) return;
            int jumlahBarang = Integer.parseInt(jumlahStr); // Mengonversi jumlah ke tipe integer

            String kondisiBarang = JOptionPane.showInputDialog(this, "Masukkan Kondisi Barang"); // Input kondisi barang
            if (kondisiBarang == null) kondisiBarang = "";

            String tanggalStr = JOptionPane.showInputDialog(this, "Masukkan Tanggal Donasi (yyyy-MM-dd)"); // Input tanggal donasi
            if (tanggalStr == null || tanggalStr.trim().isEmpty()) return;
            java.sql.Date tanggalDonasi = java.sql.Date.valueOf(tanggalStr); // Mengonversi tanggal ke tipe Date

            simpanKeDatabaseBarang(idPemberi, namaDonatur, namaBarang, jumlahBarang, kondisiBarang, tanggalDonasi); // Simpan ke database
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format jumlah barang tidak valid!"); // Pesan error jika jumlah tidak valid
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid! Gunakan format yyyy-MM-dd"); // Pesan error jika tanggal tidak valid
        }
    }

    private void lihatDonasi() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) { // Membuka koneksi ke database
            StringBuilder sb = new StringBuilder(); // StringBuilder untuk menyimpan hasil query
    
            // Query donasi uang
            String queryUang = "SELECT * FROM donasi_uang ORDER BY tanggal_donasi DESC";
            try (Statement stmt = connection.createStatement(); // Membuat statement untuk eksekusi query
                 ResultSet rs = stmt.executeQuery(queryUang)) { // Menjalankan query donasi uang
    
                sb.append("=== DONASI UANG ===\n\n"); // Menambahkan header
                while (rs.next()) { // Mengambil data setiap baris hasil query
                    sb.append(String.format("ID: %s\nNama: %s\nJumlah: Rp%.2f\nDeskripsi: %s\nTanggal: %s\n\n",
                            rs.getString("id_donasi"),
                            rs.getString("nama_donatur"),
                            rs.getDouble("jumlah_donasi"),
                            rs.getString("deskripsi_donasi"),
                            rs.getDate("tanggal_donasi")));
                }
            }
    
            // Query donasi barang
            String queryBarang = "SELECT * FROM donasi_barang ORDER BY tanggal_donasi DESC";
            try (Statement stmt = connection.createStatement(); // Membuat statement untuk eksekusi query
                 ResultSet rs = stmt.executeQuery(queryBarang)) { // Menjalankan query donasi barang
    
                sb.append("\n=== DONASI BARANG ===\n\n"); // Menambahkan header
                while (rs.next()) { // Mengambil data setiap baris hasil query
                    sb.append(String.format("ID: %s\nNama: %s\nBarang: %s\nJumlah: %d\nKondisi: %s\nTanggal: %s\n\n",
                            rs.getString("id_pemberi"),
                            rs.getString("nama_donatur"),
                            rs.getString("nama_barang"),
                            rs.getInt("jumlah_barang"),
                            rs.getString("kondisi_barang"),
                            rs.getDate("tanggal_donasi")));
                }
            }
    
            outputArea.setText(sb.toString()); // Menampilkan hasil di outputArea
            outputArea.setCaretPosition(0); // Mengatur posisi kursor ke awal
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage()); // Menangani error
            e.printStackTrace(); // Menampilkan stack trace error
        }
    }
    
    private void hapusDonasi() {
        String[] options = {"Donasi Uang", "Donasi Barang"}; // Opsi pilihan untuk menghapus donasi
        int choice = JOptionPane.showOptionDialog(this,
                "Pilih jenis donasi yang akan dihapus:", // Menampilkan dialog pilihan
                "Hapus Donasi", // Judul dialog
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]); // Menampilkan pilihan pengguna
    
        if (choice == 0) { // Jika memilih Donasi Uang
            String idDonasi = JOptionPane.showInputDialog(this, "Masukkan ID Donasi Uang yang akan dihapus:"); // Input ID donasi uang
            if (idDonasi != null && !idDonasi.trim().isEmpty()) { // Memeriksa apakah ID valid
                hapusDonasiUang(idDonasi); // Menghapus donasi uang dengan ID tersebut
            }
        } else if (choice == 1) { // Jika memilih Donasi Barang
            String idPemberi = JOptionPane.showInputDialog(this, "Masukkan ID Pemberi Barang yang akan dihapus:"); // Input ID pemberi barang
            if (idPemberi != null && !idPemberi.trim().isEmpty()) { // Memeriksa apakah ID valid
                hapusDonasiBarang(idPemberi); // Menghapus donasi barang dengan ID tersebut
            }
        }
    }
    
    private void ubahDonasi() {
        String[] options = {"Donasi Uang", "Donasi Barang"}; // Opsi pilihan untuk mengubah donasi
        int choice = JOptionPane.showOptionDialog(this,
                "Pilih jenis donasi yang akan diubah:", // Menampilkan dialog pilihan
                "Ubah Donasi", // Judul dialog
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]); // Menampilkan pilihan pengguna
    
        if (choice == 0) { // Jika memilih Donasi Uang
            ubahDonasiUang(); // Mengubah donasi uang
        } else if (choice == 1) { // Jika memilih Donasi Barang
            ubahDonasiBarang(); // Mengubah donasi barang
        }
    }
    
    private void ubahDonasiUang() {
        try {
            String idDonasi = JOptionPane.showInputDialog(this, "Masukkan ID Donasi yang akan diubah:"); // Input ID donasi uang
            if (idDonasi == null || idDonasi.trim().isEmpty()) return; // Memeriksa apakah ID valid
    
            // Cek apakah ID ada
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) { // Membuka koneksi ke database
                String checkQuery = "SELECT * FROM donasi_uang WHERE id_donasi = ?"; // Query untuk mengecek ID donasi uang
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery); // Menyiapkan statement
                checkStmt.setString(1, idDonasi); // Mengatur parameter ID
                ResultSet rs = checkStmt.executeQuery(); // Menjalankan query
    
                if (!rs.next()) { // Jika ID tidak ditemukan
                    JOptionPane.showMessageDialog(this, "ID Donasi tidak ditemukan!"); // Menampilkan pesan error
                    return; // Menghentikan proses jika ID tidak ditemukan
                }
    
                // Ambil data yang akan diubah
                String namaDonatur = JOptionPane.showInputDialog(this, "Masukkan Nama Donatur Baru:", rs.getString("nama_donatur")); // Input nama donatur baru
                if (namaDonatur == null) return; // Jika input null, hentikan proses
    
                String jumlahStr = JOptionPane.showInputDialog(this, "Masukkan Jumlah Donasi Baru:", rs.getDouble("jumlah_donasi")); // Input jumlah donasi baru
                if (jumlahStr == null) return; // Jika input null, hentikan proses
                double jumlahDonasi = Double.parseDouble(jumlahStr); // Parsing jumlah donasi baru
    
                String deskripsiDonasi = JOptionPane.showInputDialog(this, "Masukkan Deskripsi Donasi Baru:", rs.getString("deskripsi_donasi")); // Input deskripsi donasi baru
                if (deskripsiDonasi == null) return; // Jika input null, hentikan proses
    
                String tanggalStr = JOptionPane.showInputDialog(this, "Masukkan Tanggal Donasi Baru (yyyy-MM-dd):", rs.getDate("tanggal_donasi")); // Input tanggal donasi baru
                if (tanggalStr == null) return; // Jika input null, hentikan proses
                java.sql.Date tanggalDonasi = java.sql.Date.valueOf(tanggalStr); // Parsing tanggal donasi baru
    
                // Update data
                String updateQuery = "UPDATE donasi_uang SET nama_donatur = ?, jumlah_donasi = ?, " +
                                   "deskripsi_donasi = ?, tanggal_donasi = ? WHERE id_donasi = ?"; // Query update donasi uang
                PreparedStatement pstmt = conn.prepareStatement(updateQuery); // Menyiapkan statement
                pstmt.setString(1, namaDonatur); // Mengatur parameter nama donatur
                pstmt.setDouble(2, jumlahDonasi); // Mengatur parameter jumlah donasi
                pstmt.setString(3, deskripsiDonasi); // Mengatur parameter deskripsi donasi
                pstmt.setDate(4, tanggalDonasi); // Mengatur parameter tanggal donasi
                pstmt.setString(5, idDonasi); // Mengatur parameter ID donasi
    
                int rowsAffected = pstmt.executeUpdate(); // Menjalankan update query
                if (rowsAffected > 0) { // Jika data berhasil diubah
                    JOptionPane.showMessageDialog(this, "Donasi uang berhasil diubah!"); // Menampilkan pesan sukses
                    lihatDonasi(); // Menampilkan kembali daftar donasi
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengubah data: " + e.getMessage()); // Menangani error SQL
            e.printStackTrace(); // Menampilkan stack trace error
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format jumlah donasi tidak valid!"); // Menangani error format jumlah
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid! Gunakan format yyyy-MM-dd"); // Menangani error format tanggal
        }
    }
    
    private void ubahDonasiBarang() {
        try {
            String idPemberi = JOptionPane.showInputDialog(this, "Masukkan ID Pemberi yang akan diubah:"); // Input ID pemberi barang
            if (idPemberi == null || idPemberi.trim().isEmpty()) return; // Memeriksa apakah ID valid
    
            // Cek apakah ID ada
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) { // Membuka koneksi ke database
                String checkQuery = "SELECT * FROM donasi_barang WHERE id_pemberi = ?"; // Query untuk mengecek ID pemberi barang
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery); // Menyiapkan statement
                checkStmt.setString(1, idPemberi); // Mengatur parameter ID
                ResultSet rs = checkStmt.executeQuery(); // Menjalankan query
    
                if (!rs.next()) { // Jika ID tidak ditemukan
                    JOptionPane.showMessageDialog(this, "ID Pemberi tidak ditemukan!"); // Menampilkan pesan error
                    return; // Menghentikan proses jika ID tidak ditemukan
                }
    
                // Ambil data yang akan diubah
                String namaDonatur = JOptionPane.showInputDialog(this, "Masukkan Nama Donatur Baru:", rs.getString("nama_donatur")); // Input nama donatur baru
                if (namaDonatur == null) return; // Jika input null, hentikan proses
    
                String namaBarang = JOptionPane.showInputDialog(this, "Masukkan Nama Barang Baru:", rs.getString("nama_barang")); // Input nama barang baru
                if (namaBarang == null) return; // Jika input null, hentikan proses
    
                String jumlahStr = JOptionPane.showInputDialog(this, "Masukkan Jumlah Barang Baru:", rs.getInt("jumlah_barang")); // Input jumlah barang baru
                if (jumlahStr == null) return; // Jika input null, hentikan proses
                int jumlahBarang = Integer.parseInt(jumlahStr); // Parsing jumlah barang baru
    
                String kondisiBarang = JOptionPane.showInputDialog(this, "Masukkan Kondisi Barang Baru:", rs.getString("kondisi_barang")); // Input kondisi barang baru
                if (kondisiBarang == null) return; // Jika input null, hentikan proses
    
                String tanggalStr = JOptionPane.showInputDialog(this, "Masukkan Tanggal Donasi Baru (yyyy-MM-dd):", rs.getDate("tanggal_donasi")); // Input tanggal donasi baru
                if (tanggalStr == null) return; // Jika input null, hentikan proses
                java.sql.Date tanggalDonasi = java.sql.Date.valueOf(tanggalStr); // Parsing tanggal donasi baru
    
                // Update data
                String updateQuery = "UPDATE donasi_barang SET nama_donatur = ?, nama_barang = ?, " +
                                   "jumlah_barang = ?, kondisi_barang = ?, tanggal_donasi = ? WHERE id_pemberi = ?"; // Query update donasi barang
                PreparedStatement pstmt = conn.prepareStatement(updateQuery); // Menyiapkan statement
                pstmt.setString(1, namaDonatur); // Mengatur parameter nama donatur
                pstmt.setString(2, namaBarang); // Mengatur parameter nama barang
                pstmt.setInt(3, jumlahBarang); // Mengatur parameter jumlah barang
                pstmt.setString(4, kondisiBarang); // Mengatur parameter kondisi barang
                pstmt.setDate(5, tanggalDonasi); // Mengatur parameter tanggal donasi
                pstmt.setString(6, idPemberi); // Mengatur parameter ID pemberi
    
                int rowsAffected = pstmt.executeUpdate(); // Menjalankan update query
                if (rowsAffected > 0) { // Jika data berhasil diubah
                    JOptionPane.showMessageDialog(this, "Donasi barang berhasil diubah!"); // Menampilkan pesan sukses
                    lihatDonasi(); // Menampilkan kembali daftar donasi
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengubah data: " + e.getMessage()); // Menangani error SQL
            e.printStackTrace(); // Menampilkan stack trace error
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format jumlah barang tidak valid!"); // Menangani error format jumlah
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid! Gunakan format yyyy-MM-dd"); // Menangani error format tanggal
        }
    }    

    private void simpanKeDatabaseUang(String idDonasi, String namaDonatur, double jumlahDonasi,
    String deskripsiDonasi, Date tanggalDonasi) {
// SQL query untuk menyimpan donasi uang ke database
String sql = "INSERT INTO donasi_uang (id_donasi, nama_donatur, jumlah_donasi, deskripsi_donasi, tanggal_donasi) " +
"VALUES (?, ?, ?, ?, ?)";

try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); // Membuka koneksi ke database
PreparedStatement pstmt = conn.prepareStatement(sql)) { // Menyiapkan statement untuk eksekusi query

pstmt.setString(1, idDonasi); // Mengatur parameter ID donasi
pstmt.setString(2, namaDonatur); // Mengatur parameter nama donatur
pstmt.setDouble(3, jumlahDonasi); // Mengatur parameter jumlah donasi
pstmt.setString(4, deskripsiDonasi); // Mengatur parameter deskripsi donasi
pstmt.setDate(5, tanggalDonasi); // Mengatur parameter tanggal donasi

pstmt.executeUpdate(); // Eksekusi query untuk menyimpan donasi
JOptionPane.showMessageDialog(this, "Donasi uang berhasil disimpan!"); // Menampilkan pesan sukses
lihatDonasi(); // Menampilkan kembali daftar donasi

} catch (SQLException e) {
JOptionPane.showMessageDialog(this, "Error menyimpan donasi uang: " + e.getMessage()); // Menangani error SQL
e.printStackTrace(); // Menampilkan stack trace error
}
}

private void simpanKeDatabaseBarang(String idPemberi, String namaDonatur, String namaBarang,
    int jumlahBarang, String kondisiBarang, Date tanggalDonasi) {
// SQL query untuk menyimpan donasi barang ke database
String sql = "INSERT INTO donasi_barang (id_pemberi, nama_donatur, nama_barang, jumlah_barang, " +
"kondisi_barang, tanggal_donasi) VALUES (?, ?, ?, ?, ?, ?)";

try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); // Membuka koneksi ke database
PreparedStatement pstmt = conn.prepareStatement(sql)) { // Menyiapkan statement untuk eksekusi query

pstmt.setString(1, idPemberi); // Mengatur parameter ID pemberi
pstmt.setString(2, namaDonatur); // Mengatur parameter nama donatur
pstmt.setString(3, namaBarang); // Mengatur parameter nama barang
pstmt.setInt(4, jumlahBarang); // Mengatur parameter jumlah barang
pstmt.setString(5, kondisiBarang); // Mengatur parameter kondisi barang
pstmt.setDate(6, tanggalDonasi); // Mengatur parameter tanggal donasi

pstmt.executeUpdate(); // Eksekusi query untuk menyimpan donasi
JOptionPane.showMessageDialog(this, "Donasi barang berhasil disimpan!"); // Menampilkan pesan sukses
lihatDonasi(); // Menampilkan kembali daftar donasi

} catch (SQLException e) {
JOptionPane.showMessageDialog(this, "Error menyimpan donasi barang: " + e.getMessage()); // Menangani error SQL
e.printStackTrace(); // Menampilkan stack trace error
}
}

private void hapusDonasiUang(String idDonasi) {
// SQL query untuk menghapus donasi uang berdasarkan ID
String sql = "DELETE FROM donasi_uang WHERE id_donasi = ?";

try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); // Membuka koneksi ke database
PreparedStatement pstmt = conn.prepareStatement(sql)) { // Menyiapkan statement untuk eksekusi query

pstmt.setString(1, idDonasi); // Mengatur parameter ID donasi
int rowsAffected = pstmt.executeUpdate(); // Menjalankan query dan mendapatkan jumlah baris yang terpengaruh

if (rowsAffected > 0) { // Jika donasi berhasil dihapus
JOptionPane.showMessageDialog(this, "Donasi uang berhasil dihapus!"); // Menampilkan pesan sukses
lihatDonasi(); // Menampilkan kembali daftar donasi
} else {
JOptionPane.showMessageDialog(this, "ID Donasi tidak ditemukan!"); // Menampilkan pesan error jika ID tidak ditemukan
}

} catch (SQLException e) {
JOptionPane.showMessageDialog(this, "Error menghapus donasi uang: " + e.getMessage()); // Menangani error SQL
e.printStackTrace(); // Menampilkan stack trace error
}
}

private void hapusDonasiBarang(String idPemberi) {
// SQL query untuk menghapus donasi barang berdasarkan ID pemberi
String sql = "DELETE FROM donasi_barang WHERE id_pemberi = ?";

try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); // Membuka koneksi ke database
PreparedStatement pstmt = conn.prepareStatement(sql)) { // Menyiapkan statement untuk eksekusi query

pstmt.setString(1, idPemberi); // Mengatur parameter ID pemberi
int rowsAffected = pstmt.executeUpdate(); // Menjalankan query dan mendapatkan jumlah baris yang terpengaruh

if (rowsAffected > 0) { // Jika donasi berhasil dihapus
JOptionPane.showMessageDialog(this, "Donasi barang berhasil dihapus!"); // Menampilkan pesan sukses
lihatDonasi(); // Menampilkan kembali daftar donasi
} else {
JOptionPane.showMessageDialog(this, "ID Pemberi tidak ditemukan!"); // Menampilkan pesan error jika ID tidak ditemukan
}

} catch (SQLException e) {
JOptionPane.showMessageDialog(this, "Error menghapus donasi barang: " + e.getMessage()); // Menangani error SQL
e.printStackTrace(); // Menampilkan stack trace error
}
}

public static void main(String[] args) {
try {
// Set Look and Feel untuk UI
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
} catch (Exception e) {
e.printStackTrace(); // Menampilkan stack trace error jika gagal mengatur Look and Feel
}

// Menjalankan aplikasi di thread utama
SwingUtilities.invokeLater(() -> new ManajemenDonasiGUI());
}
}