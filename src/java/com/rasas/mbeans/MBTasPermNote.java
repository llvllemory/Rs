package com.rasas.mbeans;


import com.rasas.entities.Companies;
import com.rasas.entities.RsData;
import com.rasas.entities.SadGenCtnTrans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


@ManagedBean
@RequestScoped
public class MBTasPermNote implements Serializable{
    
    private String barcode = "";
    
    private int permNoteNo;
    private int permNoteId;
    private int permNoteYear;
    private int permNoteSerial;
    
    private String permNoteSadRegNber;
    private Date permNoteSadRegDate;
    private String permNoteKeyDec;
    private String permNoteKeyDecName;
    private String permNoteCtnNo;
    private String permNoteGoods;
    private int permNotePacksNo;
    
    private int rsTasDocType;
    private int rsFrom;
    private int rsTo;
    private String rsYear;
    
    private String tasCarNo;
    private String tasCarNat;
    private int carWeight;
    private int ctnWeight;
    private int grossWeight;
    private int inspDoc;
    private String tasNote;
    
    
    private List<RsData> rsDataList;
    private MBRsData mBRsData = new MBRsData();
    private MBLogin mBLogin = new MBLogin();
    private List<SadGenCtnTrans> sadGenCtnTransesList;
    
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public MBTasPermNote(){
        emf = Persistence.createEntityManagerFactory("RsPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
////////////////////////////////////////////////////////////////////////////////    
    public String checkPermNoteToClose(){
        System.out.println("com.rasas.mbeans.MBTasPermNote.checkPermNoteToClose()");
        
        if(barcode.length() == 0){
            
            MBCommon.getWarnMessage("", "يجب إدخال باركود الوثيقة!");
            return "";
            
        }else if(barcode.length() > 0 && barcode.length() < 18){
            
            MBCommon.getWarnMessage("", "خطأ في رقم الباركود, الرجاء التأكد والمحاولة مرة اخرى!");
            return "";
            
        }else if(barcode.length() == 18){
            
            sadGenCtnTransesList = new ArrayList<>();
            sadGenCtnTransesList = getPermNoteDataByNoteNo(Integer.valueOf(barcode.substring(14, 18)), Integer.valueOf(barcode.substring(7, 14)), Integer.valueOf(barcode.substring(0, 7)));
            
            if(sadGenCtnTransesList.isEmpty()){
                MBCommon.getWarnMessage("", "لم يتم العثور على وثيقة النقل, الرجاء التأكد والمحاولة مرة اخرى!");
                barcode = "";
            }else{
                
                
                permNoteId = sadGenCtnTransesList.get(0).getTransId();
                permNoteNo = sadGenCtnTransesList.get(0).getTransNo();
                permNoteYear = sadGenCtnTransesList.get(0).getTransYear();
                permNoteSerial = sadGenCtnTransesList.get(0).getPermFollowNo();
                
                permNoteSadRegNber = sadGenCtnTransesList.get(0).getKeyCuo() + "/" + sadGenCtnTransesList.get(0).getSadRegYear() + "/" + sadGenCtnTransesList.get(0).getSadRegSerial() + "/" + sadGenCtnTransesList.get(0).getSadRegNber();
                permNoteSadRegDate = sadGenCtnTransesList.get(0).getSadRegDate();

                permNoteKeyDec = sadGenCtnTransesList.get(0).getKeyDec();
                permNoteCtnNo = sadGenCtnTransesList.get(0).getCarCtnIdent();
                permNotePacksNo = sadGenCtnTransesList.get(0).getTards();
                permNoteGoods = sadGenCtnTransesList.get(0).getGoods();
                
                
                if(!sadGenCtnTransesList.isEmpty()){
                    permNoteKeyDecName = getCompanyNameById(sadGenCtnTransesList.get(0).getKeyDec());
                }
                
                MBCommon.getWarnMessage("", "الوثيقة موجودة");
                
            }
            return "";
        }
        return "";
    }

////////////////////////////////////////////////////////////////////////////////
    public void loadMaxRasas(){
        System.out.println("com.rasas.mbeans.MBTasPermNote.loadMaxRasas()");
        
        if(rsYear == null){
            MBCommon.getWarnMessage("", "يجب إختيار سنة الرصاص!");

        }

        System.out.println("------------------------ " + rsYear + ", " + rsFrom + ", " + rsTo + ", " + mBLogin.getLoggedUser().getUserCenter() + ", " + mBLogin.getLoggedUser().getUserSubCenter());
        
        rsDataList = new ArrayList<>();
        rsDataList = mBRsData.getMinOpenRsDataByCenterAndSubCenter(rsYear + "", mBLogin.getLoggedUser().getUserCenter(), mBLogin.getLoggedUser().getUserSubCenter());

        if (rsDataList.isEmpty()) {
            MBCommon.getWarnMessage("", "لم يتم العثور على رصاص غير مسدد لهذا المركز, الرجاء صرف الرصاص والمحاولة مرة اخرى!");
        } else {
            
            rsFrom = rsDataList.get(0).getRsDataPK().getRsNo();
            rsTo   = rsFrom;

            System.out.println(" ------------------------------------  rsDataList size : " + rsDataList.size() +  ", rsFrom : " + rsFrom + ", rsTo : " + rsTo);
        } 
    }

////////////////////////////////////////////////////////////////////////////////
    public List<SadGenCtnTrans> getPermNoteDataByNoteNo(int permNoteYear, int permNoteNo, int permNoteId){
        System.out.println("com.rasas.mbeans.MBTasPermNote.getPermNoteDataByNoteNo()");
        
        emf.getCache().evictAll();
                
        TypedQuery<SadGenCtnTrans> query = em.createQuery("SELECT s FROM SadGenCtnTrans s WHERE s.transYear = ?1 AND s.transNo = ?2 AND s.transId = ?3", SadGenCtnTrans.class)
                .setParameter(1, permNoteYear)
                .setParameter(2, permNoteNo)
                .setParameter(3, permNoteId);
        
        return query.getResultList();         
    }

////////////////////////////////////////////////////////////////////////////////
    public String getCompanyNameById(String companyId){
        System.out.println("com.rasas.mbeans.MBTasPermNote.getCompanyNameById()");
        
        emf.getCache().evictAll();
                
        TypedQuery<Companies> query = em.createQuery("SELECT c FROM Companies c WHERE c.decCod = ?1", Companies.class)
                .setParameter(1, companyId);
        
        return query.getSingleResult().getDecNam();   
    }
////////////////////////////////////////////////////////////////////////////////
    public void printFormData(){
        System.out.println(" ------------------------------------ " + tasCarNo);
    }
////////////////////////// Getters and Setters /////////////////////////////////

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getPermNoteNo() {
        return permNoteNo;
    }

    public void setPermNoteNo(int permNoteNo) {
        this.permNoteNo = permNoteNo;
    }

    public int getPermNoteId() {
        return permNoteId;
    }

    public void setPermNoteId(int permNoteId) {
        this.permNoteId = permNoteId;
    }

    public int getPermNoteYear() {
        return permNoteYear;
    }

    public void setPermNoteYear(int permNoteYear) {
        this.permNoteYear = permNoteYear;
    }

    public int getPermNoteSerial() {
        return permNoteSerial;
    }

    public void setPermNoteSerial(int permNoteSerial) {
        this.permNoteSerial = permNoteSerial;
    }

    public int getRsTasDocType() {
        return rsTasDocType;
    }

    public void setRsTasDocType(int rsTasDocType) {
        this.rsTasDocType = rsTasDocType;
    }

    public int getRsFrom() {
        return rsFrom;
    }

    public void setRsFrom(int rsFrom) {
        this.rsFrom = rsFrom;
    }

    public int getRsTo() {
        return rsTo;
    }

    public void setRsTo(int rsTo) {
        this.rsTo = rsTo;
    }

    public String getRsYear() {
        return rsYear;
    }

    public void setRsYear(String rsYear) {
        this.rsYear = rsYear;
    }

    
    
    
    public String getPermNoteSadRegNber() {
        return permNoteSadRegNber;
    }

    public void setPermNoteSadRegNber(String permNoteSadRegNber) {
        this.permNoteSadRegNber = permNoteSadRegNber;
    }

    public Date getPermNoteSadRegDate() {
        return permNoteSadRegDate;
    }

    public void setPermNoteSadRegDate(Date permNoteSadRegDate) {
        this.permNoteSadRegDate = permNoteSadRegDate;
    }
    
    public String getPermNoteKeyDec() {
        return permNoteKeyDec;
    }

    public void setPermNoteKeyDec(String permNoteKeyDec) {
        this.permNoteKeyDec = permNoteKeyDec;
    }

    public String getPermNoteKeyDecName() {
        return permNoteKeyDecName;
    }

    public void setPermNoteKeyDecName(String permNoteKeyDecName) {
        this.permNoteKeyDecName = permNoteKeyDecName;
    }

    public String getPermNoteCtnNo() {
        return permNoteCtnNo;
    }

    public void setPermNoteCtnNo(String permNoteCtnNo) {
        this.permNoteCtnNo = permNoteCtnNo;
    }

    public String getPermNoteGoods() {
        return permNoteGoods;
    }

    public void setPermNoteGoods(String permNoteGoods) {
        this.permNoteGoods = permNoteGoods;
    }

    public int getPermNotePacksNo() {
        return permNotePacksNo;
    }

    public void setPermNotePacksNo(int permNotePacksNo) {
        this.permNotePacksNo = permNotePacksNo;
    } 

    public String getTasCarNo() {
        return tasCarNo;
    }

    public void setTasCarNo(String tasCarNo) {
        this.tasCarNo = tasCarNo;
    }

    public String getTasCarNat() {
        return tasCarNat;
    }

    public void setTasCarNat(String tasCarNat) {
        this.tasCarNat = tasCarNat;
    }

    public int getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(int carWeight) {
        this.carWeight = carWeight;
    }

    public int getCtnWeight() {
        return ctnWeight;
    }

    public void setCtnWeight(int ctnWeight) {
        this.ctnWeight = ctnWeight;
    }

    public int getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(int grossWeight) {
        this.grossWeight = grossWeight;
    }

    public int getInspDoc() {
        return inspDoc;
    }

    public void setInspDoc(int inspDoc) {
        this.inspDoc = inspDoc;
    }

    public String getTasNote() {
        return tasNote;
    }

    public void setTasNote(String tasNote) {
        this.tasNote = tasNote;
    }
    
    
    
}
