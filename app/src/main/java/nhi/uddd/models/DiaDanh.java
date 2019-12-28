package nhi.uddd.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DiaDanh implements Parcelable {

    private String maDiaDanh;
    private String tenDiaDanh;
    private String moTa;
    private String hinhAnh;
    private int maLoaiDiaDanh;
    private Date ngayTao;

    public DiaDanh(String tenDiaDanh, String moTa, String hinhAnh, int maLoaiDiaDanh,Date ngayTao) {
        this.tenDiaDanh = tenDiaDanh;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.maLoaiDiaDanh = maLoaiDiaDanh;
        this.ngayTao = ngayTao;
    }
    public DiaDanh(){};

    protected DiaDanh(Parcel in) {
        maDiaDanh = in.readString();
        tenDiaDanh = in.readString();
        moTa = in.readString();
        hinhAnh = in.readString();
        maLoaiDiaDanh = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maDiaDanh);
        dest.writeString(tenDiaDanh);
        dest.writeString(moTa);
        dest.writeString(hinhAnh);
        dest.writeInt(maLoaiDiaDanh);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DiaDanh> CREATOR = new Creator<DiaDanh>() {
        @Override
        public DiaDanh createFromParcel(Parcel in) {
            return new DiaDanh(in);
        }

        @Override
        public DiaDanh[] newArray(int size) {
            return new DiaDanh[size];
        }
    };

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenDiaDanh() {
        return tenDiaDanh;
    }

    public void setTenDiaDanh(String tenDiaDanh) {
        this.tenDiaDanh = tenDiaDanh;
    }

    public int getMaLoaiDiaDanh() {
        return maLoaiDiaDanh;
    }

    public void setMaLoaiDiaDanh(int maLoaiDiaDanh) {
        this.maLoaiDiaDanh = maLoaiDiaDanh;
    }

    public String getMaDiaDanh() {
        return maDiaDanh;
    }

    public void setMaDiaDanh(String maDiaDanh) {
        this.maDiaDanh = maDiaDanh;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
}
