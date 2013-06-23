/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmsDao;

import java.io.IOException;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import rmsBean.EnviadasRmsBean;
import rmsBean.RecebidasRmsBean;

/**
 *
 * @author Guilherme Gehling
 */
public class RecebidasRmsDAO {

    private static final String RMSSTORE = "RMSRECEBIDAS";
    private RecordStore recordStore;

    public void open() throws RecordStoreException {
        this.recordStore = RecordStore.openRecordStore(RMSSTORE, true);
    }

    public void close() throws RecordStoreNotOpenException, RecordStoreException {
        recordStore.closeRecordStore();
    }

    public void clear() {
        try {
            RecordStore.deleteRecordStore(RMSSTORE);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public void insert(RecebidasRmsBean msg) throws IOException, RecordStoreNotOpenException, RecordStoreException {
        this.open();
        msg.setCod_msg(recordStore.getNumRecords());
        byte[] registroBytes = msg.getBytes();
        int cod_msg = recordStore.addRecord(registroBytes, 0, registroBytes.length);
        msg.setCod_msg(cod_msg);
        this.close();
    }

    public void delete(RecebidasRmsBean msg) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
        this.open();
        recordStore.deleteRecord(msg.getCod_msg());
        this.close();
    }

    public void update(RecebidasRmsBean msg) throws IOException, RecordStoreNotOpenException, RecordStoreException {
        this.open();
        byte[] registroBytes = msg.getBytes();
        recordStore.setRecord(msg.getCod_msg(), registroBytes, 0, registroBytes.length);
        this.close();
    }

    public Vector todasRecebidas() {
        try {
            this.open();
            Vector ret = new Vector();
            RecordEnumeration enumerator = recordStore.enumerateRecords(null, null, false);
            while (enumerator.hasNextElement()) {
                RecebidasRmsBean recebidas = new RecebidasRmsBean();
                recebidas.setCod_msg(enumerator.nextRecordId());
                recebidas.setBytes(recordStore.getRecord(recebidas.getCod_msg()));
                ret.addElement(recebidas.getConteudo());
            }
            this.close();
            return ret;
        } catch (InvalidRecordIDException ex) {
            return null;
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
