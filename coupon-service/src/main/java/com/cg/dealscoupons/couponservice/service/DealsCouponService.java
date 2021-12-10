package com.cg.dealscoupons.couponservice.service;

import com.cg.dealscoupons.couponservice.entity.Coupon;
import com.cg.dealscoupons.couponservice.dao.DealCouponRepository;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DealsCouponService {

    private final DealCouponRepository dealCouponRepository;

    public void addCoupon(Coupon coupon) {
        coupon.setCouponId(getNextCouponId());
        dealCouponRepository.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) {
        Optional<Coupon> byCouponId = dealCouponRepository.findByCouponId(coupon.getCouponId());
        if (byCouponId.isPresent()) {
            coupon.setId(coupon.getId());
            return dealCouponRepository.save(coupon);
        } else {
            throw new IllegalArgumentException("No such coupon exist");
        }

    }

    public void deleteCouponById(int couponId) {
        Optional<Coupon> byCouponId = dealCouponRepository.findByCouponId(couponId);
        if (byCouponId.isPresent()) {
            dealCouponRepository.deleteById(byCouponId.get().getId());
        } else {
            throw new IllegalArgumentException("No such coupon exist");
        }
    }
    public List<Coupon> getAllCoupons() {
        return dealCouponRepository.findAll();
    }

    public List<Coupon> getCouponsByCategory(String category) {
        return dealCouponRepository.findAllByCategory(category);
    }
    public List<Coupon> getByCouponType(String couponType) {
        return dealCouponRepository.findAllByCouponType(couponType);
    }


    public Optional<Coupon> getCouponById(int id) {
        return dealCouponRepository.findByCouponId(id);
    }

    @Synchronized
    public int getNextCouponId() {
        Coupon lastCoupon = dealCouponRepository.findTopByOrderByCouponIdDesc();
        int couponId = (lastCoupon ==null || lastCoupon.getCouponId() <= 0) ? 0 : lastCoupon.getCouponId();
        return ++couponId;
    }

    public Optional<Coupon> getCouponByName(String name) {
        return dealCouponRepository.findByCouponName(name);
    }
}
