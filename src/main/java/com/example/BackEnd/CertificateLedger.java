package com.example.BackEnd;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CertificateLedger {

    private List<Certificate> certificates;
    DBFactory dbFactory;


    public CertificateLedger()
    {
        certificates = new ArrayList<Certificate>(); 
        dbFactory = DBFactory.getInstance();
    }

    public CertificateLedger(int docId)
    {
        dbFactory = DBFactory.getInstance();
        populateCertificates(docId);
    }

    public void populateCertificates(int docId)
    {
        JSONArray objs = new JSONArray(dbFactory.createHandler("SQL").getCertificates(docId));

        certificates = new ArrayList<>();

        for(int i = 0; i < objs.length(); i++)
        {
            JSONObject obj = objs.getJSONObject(i);

            Certificate certificate = new Certificate(obj.toString());

            certificates.add(certificate);
        }

        System.out.println(certificates.toString());
    }

    public String getCertificates()
    {
        JSONArray objs = new JSONArray();

        for(int i = 0; i < certificates.size(); i++)
        {
            JSONObject obj = new JSONObject(certificates.get(i).toString());

            objs.put(obj);
        }

        System.out.println(objs.toString());

        return objs.toString();
    }

    public void addCertification(String info, int docId)
    {
        try
        {
            Certificate certificate = new Certificate(info);

            DBFactory.getInstance().createHandler("SQL").addCertification(certificate.toString(), docId);
            
            certificates.add(certificate);
        }
        catch(Exception e)
        {
            System.out.println(e + "\nClass: " + getClass().getName() + "\nFunction: " + new Object() {} .getClass().getEnclosingMethod().getName());

        }
    }
}
