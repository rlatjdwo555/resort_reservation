package resortreservation;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Voucher_table")
public class Voucher {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long reservId;
    private String voucherCode;
    private String voucherStatus;
    private String sendStatus;

    @PostPersist
    public void onPostPersist(){
        VoucherRequested voucherRequested = new VoucherRequested();
        BeanUtils.copyProperties(this, voucherRequested);
        voucherRequested.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){

        System.out.println("#### onPostUpdate :" + this.toString());

        if("Send".equals(this.getVoucherStatus())){
            VoucherSend voucherSend = new VoucherSend();
            BeanUtils.copyProperties(this, voucherSend);
            voucherSend.publishAfterCommit();
        }
        else if("Cancelled".equals(this.getVoucherStatus())){
            VoucherCancelled voucherCancelled = new VoucherCancelled();
            BeanUtils.copyProperties(this, voucherCancelled);
            voucherCancelled.publishAfterCommit();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getReservId() {
        return reservId;
    }

    public void setReservId(Long reservId) {
        this.reservId = reservId;
    }
    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setSendStatus(String sendStatus){
        this.sendStatus = sendStatus;
    }

    public String getSendStatus(){
        return sendStatus;
    }

}
