package com.example.BackEnd;

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
        try
        {
            dbFactory = DBFactory.getInstance();
            populateCertificates(docId);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void populateCertificates(int docId)
    {
        try
        {
            JSONArray objs = new JSONArray(dbFactory.createHandler("SQL").getCertificates(docId));

            certificates = new ArrayList<>();

            for(int i = 0; i < objs.length(); i++)
            {
                JSONObject obj = objs.getJSONObject(i);

                Certificate certificate = new Certificate(obj.toString());

                certificates.add(certificate);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getCertificates()
    {
        try
        {
            JSONArray objs = new JSONArray();

            for(int i = 0; i < certificates.size(); i++)
            {
                JSONObject obj = new JSONObject(certificates.get(i).toString());

                objs.put(obj);
            }

            return objs.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public void addCertification(String info, int docId)throws Exception
    {
        try
        {
            Certificate certificate = new Certificate(info);

            DBFactory.getInstance().createHandler("SQL").addCertification(certificate.toString(), docId);
            
            System.out.println(certificate.toString());
            certificates.add(certificate);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
