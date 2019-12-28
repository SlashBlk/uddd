package nhi.uddd.models;

import java.util.Date;

public class Comment {


    private String hinhAnhNguoiDung;
    private String tenNguoiDung;
    private Date ngayTao;
    private String noiDung;

    public Comment(){

    }

    public Comment(String tenNguoiDung,String hinhAnhNguoiDung, Date ngayTao, String noiDung) {
        this.hinhAnhNguoiDung = hinhAnhNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.ngayTao = ngayTao;
        this.noiDung = noiDung;
    }

    public String getHinhAnhNguoiDung() {
        return hinhAnhNguoiDung;
    }

    public void setHinhAnhNguoiDung(String hinhAnhNguoiDung) {
        this.hinhAnhNguoiDung = hinhAnhNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
