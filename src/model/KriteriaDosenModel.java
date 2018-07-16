/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entitas.KriteriaDosen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import utility.ConnectionUtility;

/**
 *
 * @author Fadli
 */
public class KriteriaDosenModel {

    private Connection con;
    private List<KriteriaDosen> list;
    private Object[][] arr;

    public KriteriaDosenModel() {
        con = ConnectionUtility.getConnection();
        list = new ArrayList<>();
    }

    public List<KriteriaDosen> getAll() {
        try {
            String sql = "SELECT * FROM kriteria_dosen ORDER BY nidn, id_kriteria";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = new ArrayList<>();
            while (resultSet.next()) {
                KriteriaDosen kriteria_dosen = new KriteriaDosen(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                list.add(kriteria_dosen);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Retrieve Data\n" + ex);
        }
        return list;
    }

    public List<KriteriaDosen> getAll(String nidn) {
        try {
            String sql = "SELECT kd.nidn, k.nama, s.nama FROM kriteria_dosen kd, "
                    + "subkriteria s, kriteria k WHERE kd.id_kriteria=k.id AND kd.nidn='" + nidn + "' AND kd.id_himpunan=s.id ORDER BY kd.id_kriteria";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = new ArrayList<>();
            while (resultSet.next()) {
                KriteriaDosen kriteria_dosen = new KriteriaDosen(resultSet.getString(1), null, null, resultSet.getString(2), resultSet.getString(3));
                list.add(kriteria_dosen);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Retrieve Data\n" + ex);
        }
        return list;
    }

    public Object[][] getListbarang() {
        String sql = "SELECT * FROM kriteria_dosen ORDER BY nidn, id_kriteria";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            arr = new Object[rows][3];
            for (int i = 0; i < rows; i++) {
                rs.next();
                arr[i][0] = rs.getString(1);
                arr[i][1] = rs.getString(2);
                arr[i][2] = rs.getString(3);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data barang gagal ditampilkan\n" + e.getMessage());
        }
        return arr;
    }

    public String[] getKriteria() {
        String sql = "SELECT nama FROM kriteria ORDER BY id";
        PreparedStatement statement = null;
        String[] namaKriteria = null;
        try {
            statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.last();
            int rows = rs.getRow();
            namaKriteria = new String[rows];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                namaKriteria[i] = rs.getString(1);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data barang gagal ditampilkan\n" + e.getMessage());
        }
        return namaKriteria;
    }

    public String[] getDosen() {
        String sql = "SELECT nama FROM dosen ORDER BY nidn";
        PreparedStatement statement = null;
        String[] namaDosen = null;
        try {
            statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.last();
            int rows = rs.getRow();
            namaDosen = new String[rows];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                namaDosen[i] = rs.getString(1);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data barang gagal ditampilkan\n" + e.getMessage());
        }
        return namaDosen;
    }

    public KriteriaDosen getKriteriaDosen(String nidn) {
        KriteriaDosen kriteria_dosen = null;
        try {
            String sql = "SELECT * FROM kriteria_dosen WHERE nidn=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, nidn);
            ResultSet resultSet = prepare.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                kriteria_dosen = new KriteriaDosen(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println("" + ex);
        }
        return kriteria_dosen;
    }

    public Integer getNilai(String id_himpunan) {
        Integer kode = 0;
        String sql = "SELECT bobot FROM subkriteria WHERE id = '" + id_himpunan + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                kode = resultSet.getInt(1);
            }
            return kode;
        } catch (SQLException e) {
            return null;
        }
    }

    public String getNidn() {
        String nidn = "";
        String sql = "SELECT nidn FROM kriteria_dosen ORDER BY nidn DESC";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                nidn = resultSet.getString(1);
            }
        } catch (SQLException e) {

        }
        return nidn;
    }

    public String[] getNamaDosen() {
        String[] kode;
        String sql = "SELECT nama FROM dosen ORDER BY nidn";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            kode = new String[resultSet.getRow()];
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()) {
                kode[i] = resultSet.getString(1);
                i++;
            }
            return kode;
        } catch (SQLException e) {
            return null;
        }
    }

    public String[] getNamaKriteria() {
        String[] kode;
        String sql = "SELECT nama FROM kriteria ORDER BY id";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            kode = new String[resultSet.getRow()];
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()) {
                kode[i] = resultSet.getString(1);
                i++;
            }
            return kode;
        } catch (SQLException e) {
            return null;
        }
    }

    public String[] getIdKriteria() {
        String[] kode;
        String sql = "SELECT id FROM kriteria ORDER BY id";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            kode = new String[resultSet.getRow()];
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()) {
                kode[i] = resultSet.getString(1);
                i++;
            }
            return kode;
        } catch (SQLException e) {
            return null;
        }
    }

    public String[] getSubkriteria(String id_kriteria) {
        String[] kode;
        String sql = "SELECT nama FROM subkriteria WHERE id_kriteria='" + id_kriteria + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            kode = new String[resultSet.getRow()];
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()) {
                kode[i] = resultSet.getString(1);
                i++;
            }
            return kode;
        } catch (SQLException e) {
            return null;
        }
    }

    public String getNidn(String nama) {
        String id = "";
        String sql = "SELECT nidn FROM dosen WHERE nama='" + nama + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                id = resultSet.getString(1);
            }
        } catch (SQLException e) {

        }
        return id;
    }

    public String getIdKriteria(String nama) {
        String id = "";
        String sql = "SELECT id FROM kriteria WHERE nama='" + nama + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                id = resultSet.getString(1);
            }
        } catch (SQLException e) {

        }
        return id;
    }

    public String getIdSubKriteria(String id_kriteria, String nama) {
        String id = "";
        String sql = "SELECT id FROM subkriteria WHERE nama='" + nama + "' AND id_kriteria='" + id_kriteria + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                id = resultSet.getString(1);
            }
        } catch (SQLException e) {

        }
        return id;
    }

    public Integer getBobot(String id) {
        Integer bobot = 0;
        String sql = "SELECT bobot FROM kriteria WHERE id='" + id + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                bobot = resultSet.getInt(1);
            }
        } catch (SQLException e) {

        }
        return bobot;
    }

    public boolean isExist(String nidn, String id_kriteria) {
        boolean id = false;
        String sql = "SELECT * FROM kriteria_dosen WHERE nidn='" + nidn + "' AND id_kriteria='" + id_kriteria + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                id = true;
            }
        } catch (SQLException e) {

        }
        return id;
    }

    public boolean insert(KriteriaDosen kriteria_dosen) {
        String sql = "INSERT INTO kriteria_dosen (nidn, id_kriteria, id_himpunan) VALUES (?, ?, ?)";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for (int i = 0; i < 3; i++) {
                prepare.setObject(i + 1, kriteria_dosen.getObject(i));
            }
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "NIDN Dengan Kriteria Yang Anda Inputkan Sudah Terdaftar");
            try {
                con.rollback();
                prepare.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error When Rollback Connection\n" + ex1);
            }
            return false;
        }
        return true;
    }

    public boolean update(KriteriaDosen kriteria_dosen) {
        String sql = "UPDATE kriteria_dosen SET id_kriteria=?, id_himpunan=? WHERE nidn=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for (int i = 1; i < 3; i++) {
                prepare.setObject(i, kriteria_dosen.getObject(i));
                prepare.setObject(3, kriteria_dosen.getNidn());
            }
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Update Data\n" + ex);
            try {
                con.rollback();
                prepare.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error When Rollback Connection\n" + ex1);
            }
            return false;
        }
        return true;
    }

    public boolean delete(String nidn) {
        String sql = "DELETE FROM kriteria_dosen WHERE nidn=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            prepare.setString(1, nidn);
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Delete Data\n" + ex);
            try {
                con.rollback();
                prepare.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error When Rollback Connection\n" + ex1);
            }
            return false;
        }
        return true;
    }

    public boolean insertKinerja(String nidn, Double nilai, String kinerja) {
        String sql = "INSERT INTO hasil_kinerja (nidn, nilai, kinerja) VALUES (?, ?, ?)";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            prepare.setString(1, nidn);
            prepare.setDouble(2, nilai);
            prepare.setString(3, kinerja);
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            try {
                con.rollback();
                prepare.close();
                System.out.println(ex.getMessage());
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error When Rollback Connection\n" + ex1);
            }
            return false;
        }
        return true;
    }

    public void truncate() {
        String sql = "TRUNCATE TABLE hasil_kinerja";
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("" + e);
        }
    }
}
