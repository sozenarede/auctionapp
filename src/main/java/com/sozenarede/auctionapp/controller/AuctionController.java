package com.sozenarede.auctionapp.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionController {

    private static final int MAXBIDS = 999;
    private static final double BIDEPRICE  = 0.98;
    private static HashMap<String, Double> bids;
    private static ArrayList<Double> invalidValues;
    private static Result result;

    private static ResponseEntity<String> startBids(){
        try {
            bids = new HashMap<>();
            invalidValues = new ArrayList<>();
            return new ResponseEntity<>("ACCEPTED", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            //TODO: handle exception
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    private static ResponseEntity<String> addBid(String bidownerStr, String bidvalueStr) {
        Iterator<String> it = bids.keySet().iterator();
        DecimalFormat df = new DecimalFormat("#.00");
        Double requestValue = Double.parseDouble(bidvalueStr);
        requestValue = Double.parseDouble(df.format(requestValue));
        String requestKey = bidownerStr;

        if (invalidValues.size() >= MAXBIDS) {
            return new ResponseEntity<>("ACCEPTED Ignoring MAXBIDS", HttpStatus.NOT_EXTENDED);
        }

        if (requestValue < 0) {
            return new ResponseEntity<>("BAD_REQUEST Ignoring Less then zero", HttpStatus.BAD_REQUEST);
        }

        if (bids.isEmpty()) {
            try {
                bids.put(requestKey, requestValue);
                invalidValues.add(requestValue);
                return new ResponseEntity<>("ACCEPTED Adding new", HttpStatus.ACCEPTED);
            } catch (Exception e) {
                //TODO: handle exception
                return new ResponseEntity<>("ERROR Adding new", HttpStatus.BAD_REQUEST);
            }
        }
        else {
            while (it.hasNext()) {
                String bidKey = it.next();
                Double bidvalue = bids.get(bidKey);
                                
                if (bids.containsValue(requestValue)) {
                    if (bidvalue.equals(requestValue)) {
                        try {
                            bids.remove(bidKey);
                            invalidValues.add(requestValue);
                            return new ResponseEntity<>("ACCEPTED Removing bidd", HttpStatus.ACCEPTED);
                        } catch (Exception e) {
                            //TODO: handle exception
                            return new ResponseEntity<>("ERROR Removing bids", HttpStatus.BAD_REQUEST);
                        }
                    }
                } else {
                    if (invalidValues.contains(requestValue)) {
                        try {
                            invalidValues.add(requestValue);
                            return new ResponseEntity<>("ACCEPTED Ignoring invalidValues", HttpStatus.ACCEPTED);
                        } catch (Exception e) {
                            //TODO: handle exception
                            return new ResponseEntity<>("ERROR Adding invalidValues", HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        try {
                            bids.put(requestKey, requestValue);
                            invalidValues.add(requestValue);
                            return new ResponseEntity<>("ACCEPTED Adding new", HttpStatus.ACCEPTED);
                        } catch (Exception e) {
                            //TODO: handle exception
                            return new ResponseEntity<>("ERROR Adding new", HttpStatus.BAD_REQUEST);
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>("ERROR Geral", HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<String> closeAuction() {
        String name = "";
        double min = 100.00;
        double profit = 0.00;


        Iterator<String> it = bids.keySet().iterator();
        while (it.hasNext()) {
            String bidKey = it.next();
            Double bidvalue = bids.get(bidKey);

            if (min > bidvalue) {
                min = bidvalue;
                name = bidKey;
                profit = BIDEPRICE * invalidValues.size();
            }
        }

        result = new Result(name, min, profit);
        bids.clear();
        invalidValues.clear();
        return new ResponseEntity<>("ACCEPTED", HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/api/auction/start")
    public ResponseEntity<String> setAuctionStart() {
        //Pensando como fazer
        return startBids();
 
    }

    @GetMapping(path = "/api/auction/sendbid")
    public ResponseEntity<String> sendBid(@RequestParam(value = "bidowner", required = true) String bidowner,
                                  @RequestParam(value = "bidvalue" , required = true) String bidvalue) {
        //Pensando como fazer
        return addBid(bidowner, bidvalue);
    }

    @GetMapping(path = "/api/auction/end")
    public ResponseEntity<String> setAuctionEnd() {
        //Pensando como fazer

       return closeAuction();

    }

    @GetMapping(path = "/api/auction/bids")
    public Map<String, Double> setAuctionBids() {
        //Pensando como fazer

        return bids;
    }


    @GetMapping(path = "/api/auction/res")
    public Result setAuctionResult() {
        //Pensando como fazer

        return result;
    }
}
