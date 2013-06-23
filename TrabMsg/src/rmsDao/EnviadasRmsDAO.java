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

/**
 *
 * @author Guilherme Gehling
 */
public class EnviadasRmsDAO {

    private static final String RMSSTORE = "RMSENVIADAS";
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

    public void insert(EnviadasRmsBean msg) throws IOException, RecordStoreNotOpenException, RecordStoreException {
        this.open();
        msg.setCod_msg(recordStore.getNumRecords());
        byte[] registroBytes = msg.getBytes();
        int cod_msg = recordStore.addRecord(registroBytes, 0, registroBytes.length);
        msg.setCod_msg(cod_msg);
        this.close();
    }

    public void delete(EnviadasRmsBean msg) throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException {
        this.open();
        recordStore.deleteRecord(msg.getCod_msg());
        this.close();
    }

    public void update(EnviadasRmsBean msg) throws IOException, RecordStoreNotOpenException, RecordStoreException {
        this.open();
        byte[] registroBytes = msg.getBytes();
        recordStore.setRecord(msg.getCod_msg(), registroBytes, 0, registroBytes.length);
        this.close();
    }

    public Vector todasEnviadas() {
        try {
            this.open();
            Vector ret = new Vector();
            RecordEnumeration enumerator = recordStore.enumerateRecords(null, null, false);
            while (enumerator.hasNextElement()) {
                EnviadasRmsBean enviadas = new EnviadasRmsBean();
                enviadas.setCod_msg(enumerator.nextRecordId());
                enviadas.setBytes(recordStore.getRecord(enviadas.getCod_msg()));
                ret.addElement(enviadas.getConteudo());
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
