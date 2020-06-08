package com.zzikza.springboot.web.dto;

import com.zzikza.springboot.web.domain.enums.EBoardCategory;
import com.zzikza.springboot.web.domain.enums.ETermStatus;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.sale.Sale;
import com.zzikza.springboot.web.domain.studio.StudioBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentRequestDto {
    private Sale sale;
    private String title;
    private String content;
    private int fileLength;
    private EBoardCategory brdCateCd;
    private String pg;
    private String pay_method;
    private String amount;
    private String buyer_email;
    private String buyer_name;
    private String company;
    private boolean digital;
    private String prdId;
    private String studioId;
    ReservationRequestDto reservation;
    public ETermStatus cancelTermYn;
    public ETermStatus customRequest;
    public ETermStatus otherTermYn;
    public ETermStatus privateTermYn;
    public ETermStatus useTermYn;
    public String tel;
    public String userName;
    public String saleCode;
    public String exhId;

    @Builder
    public PaymentRequestDto(String title, String content, int fileLength, EBoardCategory brdCateCd) {
        this.title = title;
        this.content = content;
        this.fileLength = fileLength;
        this.brdCateCd = brdCateCd;
    }

    public StudioBoard toEntity() {
        return StudioBoard.builder()
                .title(getTitle())
                .content(getContent())
                .boardCategoryCode(getBrdCateCd())
                .build();
    }
    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }
}
