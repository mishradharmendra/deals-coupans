package com.cg.dealscoupons.couponservice.controller;

import com.cg.dealscoupons.couponservice.entity.Coupon;
import com.cg.dealscoupons.couponservice.service.DealsCouponService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coupon")
@AllArgsConstructor
@Slf4j
public class DealsCouponsController {

    private final DealsCouponService dealsCouponService;

    @PostMapping(value = "/createCoupon", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCoupon(@RequestHeader(name = "X-User") String userHeader,
                                              @RequestBody Coupon coupon) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeReference = new TypeReference<>(){};
        HashMap<String, String> map = mapper.readValue(userHeader, typeReference);
        Object roles = map.get("Roles");

        if( roles.equals("ROLES_ADMIN")) {
            log.info("Received Request from user " + userHeader);
            dealsCouponService.addCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/allCoupons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(dealsCouponService.getAllCoupons());
    }


    @GetMapping(value = "/id/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Coupon>> getAllById(@RequestParam (value = "couponId") int couponId ) {
        return ResponseEntity.ok(dealsCouponService.getCouponById(couponId));
    }

    @GetMapping(value = "/type/{couponType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getAllByCouponType(@RequestParam (value = "couponType") String couponType ) {
        return ResponseEntity.ok(dealsCouponService.getByCouponType(couponType));
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Coupon>> getByName(@RequestParam (value = "name") String name ) {
        return ResponseEntity.ok(dealsCouponService.getCouponByName(name));
    }

    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getByCategory(@RequestParam (value = "category") String category ) {
        return ResponseEntity.ok(dealsCouponService.getCouponsByCategory(category));
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coupon> update(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(dealsCouponService.updateCoupon(coupon));
    }

    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<Void> delete(@RequestParam(value = "couponId") int couponId) {
        dealsCouponService.deleteCouponById(couponId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
