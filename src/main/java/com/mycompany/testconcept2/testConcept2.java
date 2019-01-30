/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testconcept2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author exk
 */
public class testConcept2 {

    private static testConcept2 myApp;
    private static String apiRUL;
    private static String userName;
    private static String password;

    private static PaymentTransaction paymentTransaction;

    public static void main(String[] args) {

        System.out.println("Enter your API URL e.g. google.com: ");
        Scanner scanner = new Scanner(System.in);
        apiRUL = scanner.nextLine();

        System.out.println("Enter your username: ");
        scanner = new Scanner(System.in);
        userName = scanner.nextLine();

        System.out.println("Enter your password: ");
        scanner = new Scanner(System.in);
        password = scanner.nextLine();

        paymentTransaction = new PaymentTransaction();
        paymentTransaction.setAdditionalInfo("");
        paymentTransaction.setAmount(3000.0);
        paymentTransaction.setCustomerName("Elisha Kalya");
        paymentTransaction.setMobileNumber("254723745192");
        paymentTransaction.setTillNumber("0789567321");

        paymentTransaction.setTimeStamp(new Date());
        paymentTransaction.setTransactionRefNo(paymentTransaction.generateTransaction(12));


        getInstance().doPost();
        getInstance().doUpdate();
        getInstance().doGetByTillNumber();
        getInstance().doGetByTillNumberAndMobileNumber();
    }

    public static testConcept2 getInstance() {
        if (myApp == null) {
            myApp = new testConcept2();
        }
        return myApp;
    }

    public void doGetByTillNumber() {

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpclient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        try {
            HttpHost target = new HttpHost(apiRUL, 443, "https");
            String uri;
            uri = "/paymentconfirmations/tillnumber/" + paymentTransaction.getTillNumber();
            // uri="/paymentconfirmations/";
            HttpGet getRequest = new HttpGet(uri);
            System.out.println("executing request to " + target);
            HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpEntity entity = httpResponse.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (IOException | ParseException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public void doGetByTillNumberAndMobileNumber() {

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpclient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        try {
            HttpHost target = new HttpHost(apiRUL, 443, "https");
            String uri;
            uri = "/paymentconfirmations/tillnumber/" + paymentTransaction.getTillNumber() + "/mobilenumber/" + paymentTransaction.getMobileNumber() + "";
            // uri="/paymentconfirmations/";
            HttpGet getRequest = new HttpGet(uri);
            System.out.println("executing request to " + target);
            HttpResponse httpResponse = httpclient.execute(target, getRequest);
            HttpEntity entity = httpResponse.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (IOException | ParseException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public void doPost() {

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpclient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        try {
            HttpHost target = new HttpHost(apiRUL, 443, "https");
            String uri;
            uri = "/paymentconfirmations/";
            HttpPost postRequest = new HttpPost(uri);

            String json = paymentTransaction.toString();

            StringEntity transactionEntity = new StringEntity(json);
            postRequest.setEntity(transactionEntity);
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Content-type", "application/json");

            System.out.println("executing request to " + target);
            HttpResponse httpResponse = httpclient.execute(target, postRequest);
            HttpEntity entity = httpResponse.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (IOException | ParseException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public void doUpdate() {

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpclient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        try {
            HttpHost target = new HttpHost(apiRUL, 443, "https");
            String uri;
            uri = "/paymentconfirmations/";
            HttpPut putRequest = new HttpPut(uri);
            String json = "{\n"
                    + "  \"paymentConfirmation\": {\n"
                    + "    \"transactionId\": 17,\n"
                    + "    \"posTransactionNumber\": \"123\"\n"
                    + "  }\n"
                    + "}";
            StringEntity transactionEntity = new StringEntity(json);
            putRequest.setEntity(transactionEntity);
            putRequest.setHeader("Accept", "application/json");
            putRequest.setHeader("Content-type", "application/json");

            System.out.println("executing request to " + target);
            HttpResponse httpResponse = httpclient.execute(target, putRequest);
            HttpEntity entity = httpResponse.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (IOException | ParseException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    private static class PaymentTransaction {

        private String tillNumber;
        private String mobileNumber;
        private Double amount;
        private Date timeStamp;
        private String transactionRefNo;
        private String servedBy;
        private String additionalInfo;
        private String posTransactionNumber;
        private String customerName;

        public PaymentTransaction() {

        }

        @Override
        public String toString() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            System.out.println(df.format(new Date()));
            String json = "{\n"
                    + "  \"paymentConfirmation\":\n"
                    + "    {\n"
                    + "      \"tillNumber\": \"" + this.getTillNumber() + "\",\n"
                    + "      \"mobileNumber\": \"" + this.getMobileNumber() + "\",\n"
                    + "      \"amount\": " + this.getAmount() + ",\n"
                    + "      \"timeStamp\": \"" + df.format(this.getTimeStamp()) + "\",\n"
                    + "      \"transactionRefNo\": \"" + this.getTransactionRefNo() + "\",\n"
                    + "      \"servedBy\": \"" + this.getServedBy() + "\",\n"
                    + "      \"additionalInfo\": \"" + this.getAdditionalInfo() + "\",\n"
                    + "      \"posTransactionNumber\": \"" + this.getPosTransactionNumber() + "\",\n"
                    + "      \"customerName\": \"" + this.getCustomerName() + "\"\n"
                    + "    }\n"
                    + "}";

            return json;
        }

        public String getTillNumber() {
            return tillNumber;
        }

        public void setTillNumber(String tillNumber) {
            this.tillNumber = tillNumber;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Date getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(Date timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getTransactionRefNo() {
            return transactionRefNo;
        }

        public void setTransactionRefNo(String transactionRefNo) {
            this.transactionRefNo = transactionRefNo;
        }

        public String getServedBy() {
            return servedBy;
        }

        public void setServedBy(String servedBy) {
            this.servedBy = servedBy;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
        }

        public String getPosTransactionNumber() {
            return posTransactionNumber;
        }

        public void setPosTransactionNumber(String posTransactionNumber) {
            this.posTransactionNumber = posTransactionNumber;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        private String generateTransaction(Integer len) {
            final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            final int N = alphabet.length();

            Random r = new Random();
            String code = "";
            for (int i = 0; i < len; i++) {
                code += alphabet.charAt(r.nextInt(N));
            }

            return code;
        }

    }
}
