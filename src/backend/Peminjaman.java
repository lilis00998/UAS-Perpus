package backend;

import java.util.ArrayList;
import java.sql.ResultSet;


public class Peminjaman {
    private int idPeminjaman;
    private Anggota anggota = new Anggota();
    private Buku buku = new Buku();
    private String tanggalPinjam;
    private String tanggalKembali;

    public Peminjaman() {}

    public Peminjaman(Anggota anggota, Buku buku, String tglPinjam, String tglKembali) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tglPinjam;
        this.tanggalKembali = tglKembali;
    }

    // Getter dan Setter
    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public Peminjaman getById(int id) {
        Peminjaman pj = new Peminjaman();
        ResultSet rs = DBHelper.selectQuery(
            "SELECT * FROM peminjaman p " +
            "LEFT JOIN anggota a ON p.idanggota = a.idanggota " +
            "LEFT JOIN buku b ON p.idbuku = b.idbuku " +
            "WHERE p.idpeminjaman = " + id
        );

        try {
            while (rs.next()) {
                pj = new Peminjaman();
                pj.setIdPeminjaman(rs.getInt("idpeminjaman"));
                pj.getAnggota().setIdAnggota(rs.getInt("idanggota"));
                pj.getAnggota().setNama(rs.getString("nama"));
                pj.getAnggota().setAlamat(rs.getString("alamat"));
                pj.getAnggota().setTelepon(rs.getString("telepon"));

                pj.getBuku().setIdbuku(rs.getInt("idbuku"));
                pj.getBuku().setJudul(rs.getString("judul"));
                pj.getBuku().setPenulis(rs.getString("penulis"));
                pj.getBuku().setPenerbit(rs.getString("penerbit"));

                pj.setTanggalPinjam(rs.getString("tanggalpinjam"));
                pj.setTanggalKembali(rs.getString("tanggalkembali"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pj;
    }

    public ArrayList<Peminjaman> getAll() {
        ArrayList<Peminjaman> ListPeminjaman = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery(
            "SELECT * FROM peminjaman p " +
            "LEFT JOIN anggota a ON p.idanggota = a.idanggota " +
            "LEFT JOIN buku b ON p.idbuku = b.idbuku"
        );

        try {
            while (rs.next()) {
                Peminjaman pj = new Peminjaman();
                pj.setIdPeminjaman(rs.getInt("idpeminjaman"));
                pj.getAnggota().setIdAnggota(rs.getInt("idanggota"));
                pj.getAnggota().setNama(rs.getString("nama"));
                pj.getAnggota().setAlamat(rs.getString("alamat"));
                pj.getAnggota().setTelepon(rs.getString("telepon"));

                pj.getBuku().setIdbuku(rs.getInt("idbuku"));
                pj.getBuku().setJudul(rs.getString("judul"));
                pj.getBuku().setPenulis(rs.getString("penulis"));
                pj.getBuku().setPenerbit(rs.getString("penerbit"));

                pj.setTanggalPinjam(rs.getString("tanggalpinjam"));
                pj.setTanggalKembali(rs.getString("tanggalkembali"));

                ListPeminjaman.add(pj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListPeminjaman;
    }

    public ArrayList<Peminjaman> search(String keyword) {
        ArrayList<Peminjaman> ListPeminjaman = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery(
            "SELECT * FROM peminjaman p " +
            "LEFT JOIN anggota a ON p.idanggota = a.idanggota " +
            "LEFT JOIN buku b ON p.idbuku = b.idbuku " +
            "WHERE b.judul LIKE '%" + keyword + "%' " +
            "   OR a.nama LIKE '%" + keyword + "%'"
        );

        try {
            while (rs.next()) {
                Peminjaman pj = new Peminjaman();
                pj.setIdPeminjaman(rs.getInt("idpeminjaman"));
                pj.getAnggota().setIdAnggota(rs.getInt("idanggota"));
                pj.getAnggota().setNama(rs.getString("nama"));
                pj.getAnggota().setAlamat(rs.getString("alamat"));
                pj.getAnggota().setTelepon(rs.getString("telepon"));

                pj.getBuku().setIdbuku(rs.getInt("idbuku"));
                pj.getBuku().setJudul(rs.getString("judul"));
                pj.getBuku().setPenulis(rs.getString("penulis"));
                pj.getBuku().setPenerbit(rs.getString("penerbit"));

                pj.setTanggalPinjam(rs.getString("tanggalpinjam"));
                pj.setTanggalKembali(rs.getString("tanggalkembali"));

                ListPeminjaman.add(pj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListPeminjaman;
    }

    public void save() {
        if (getById(idPeminjaman).getIdPeminjaman() == 0) {
            String SQL = "INSERT INTO peminjaman (idanggota, idbuku, tanggalpinjam, tanggalkembali) VALUES(" +
                         this.getAnggota().getIdAnggota() + ", " +
                         this.getBuku().getIdbuku() + ", " +
                         "'" + this.tanggalPinjam + "', " +
                         "'" + this.tanggalKembali + "')";
            this.idPeminjaman = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE peminjaman SET " +
                         "idanggota = " + this.getAnggota().getIdAnggota() + ", " +
                         "idbuku = " + this.getBuku().getIdbuku() + ", " +
                         "tanggalpinjam = '" + this.tanggalPinjam + "', " +
                         "tanggalkembali = '" + this.tanggalKembali + "' " +
                         "WHERE idpeminjaman = " + this.idPeminjaman;
            DBHelper.executeQuery(SQL);
        }
    }

    public void delete() {
        String SQL = "DELETE FROM peminjaman WHERE idpeminjaman = " + this.idPeminjaman;
        DBHelper.executeQuery(SQL);
    }
}