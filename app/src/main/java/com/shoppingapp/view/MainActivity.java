package com.shoppingapp.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.shoppingapp.R;
import com.shoppingapp.application.ApplicationController;
import com.shoppingapp.model.ProductCategory;
import com.shoppingapp.model.ProductType;
import com.shoppingapp.model.entity.Product;
import com.shoppingapp.model.entity.ProductThumbnail;
import com.shoppingapp.model.repo.ShoppingRepo;
import com.shoppingapp.util.SharedPreference;
import com.shoppingapp.view.adapter.ProductThumbnailRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_electronics)
    RecyclerView mRecyclerViewElectronics;

    @BindView(R.id.recycler_view_furniture)
    RecyclerView mRecyclerViewFurnitures;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cart_btn)
    ImageView cartBtn;

    private ProductThumbnailRecyclerAdapter mAdapterElectronics;
    private ProductThumbnailRecyclerAdapter mAdapterFurnitures;

    private List<ProductThumbnail> electronicProductsThumbnails;
    private List<ProductThumbnail> furnitureProductsThumbnails;

    private ShoppingRepo shoppingRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        ButterKnife.bind(this);
        shoppingRepo = ApplicationController.getAppInstance().getShoppingRepo();

        electronicProductsThumbnails = new ArrayList<>();
        furnitureProductsThumbnails = new ArrayList<>();

        toolbar.setTitle(R.string.home_title);
        toolbar.setTitleTextColor(Color.WHITE);

//        deleteAllProductThumbnails();

        setRecyclerViewAttributes();
        setValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void deleteAllProductThumbnails() {
        shoppingRepo.deleteAllProductThumbnails();
    }

    private void addProductThumbNails() {
        ProductThumbnail laptopThumbnail = new ProductThumbnail(ProductCategory.ELECTRONICS.toString(), ProductType.LAPTOP.toString(), R.drawable.laptop_home);
        ProductThumbnail fridgeThumbnail = new ProductThumbnail(ProductCategory.ELECTRONICS.toString(), ProductType.FRIDGE.toString(), R.drawable.fridge_home);
        ProductThumbnail vaccumCleanerThumbnail = new ProductThumbnail(ProductCategory.ELECTRONICS.toString(), ProductType.VACCUM_CLEANER.toString(), R.drawable.vaccum_cleaner_home);
        ProductThumbnail cotThumbnail = new ProductThumbnail(ProductCategory.FURNITURES.toString(), ProductType.COT.toString(), R.drawable.cot_home);
        ProductThumbnail cupboardThumbnail = new ProductThumbnail(ProductCategory.FURNITURES.toString(), ProductType.CUP_BOARD.toString(), R.drawable.cupboard_home);
        ProductThumbnail chairThumbnail = new ProductThumbnail(ProductCategory.FURNITURES.toString(), ProductType.CHAIR.toString(), R.drawable.chair_home);

        shoppingRepo.addProductThumbnail(laptopThumbnail);
        shoppingRepo.addProductThumbnail(fridgeThumbnail);
        shoppingRepo.addProductThumbnail(vaccumCleanerThumbnail);
        shoppingRepo.addProductThumbnail(cotThumbnail);
        shoppingRepo.addProductThumbnail(cupboardThumbnail);
        shoppingRepo.addProductThumbnail(chairThumbnail);
    }

    private void addElectronicProducts() {
        Product macLaptop = new Product("MacBook", ProductCategory.ELECTRONICS.toString(), ProductType.LAPTOP.toString(), R.drawable.mac, 85000.00);
        Product lenovaLaptop = new Product("Lenova", ProductCategory.ELECTRONICS.toString(), ProductType.LAPTOP.toString(), R.drawable.lenovo, 40000.00);
        Product dellLaptop = new Product("Dell", ProductCategory.ELECTRONICS.toString(), ProductType.LAPTOP.toString(), R.drawable.dell, 35000.00);
        Product asusLaptop = new Product("Asus", ProductCategory.ELECTRONICS.toString(), ProductType.LAPTOP.toString(), R.drawable.asus, 30000.00);
        Product acerLaptop = new Product("Acer", ProductCategory.ELECTRONICS.toString(), ProductType.LAPTOP.toString(), R.drawable.acer, 25000.00);

        Product godrejFridge = new Product("Godrej", ProductCategory.ELECTRONICS.toString(), ProductType.FRIDGE.toString(), R.drawable.godrej, 45000.00);
        Product haireFridge = new Product("Haire", ProductCategory.ELECTRONICS.toString(), ProductType.FRIDGE.toString(), R.drawable.haier, 40000.00);
        Product lgFridge = new Product("LG", ProductCategory.ELECTRONICS.toString(), ProductType.FRIDGE.toString(), R.drawable.lg, 65000.00);
        Product whirlpoolFridge = new Product("WhirlPool", ProductCategory.ELECTRONICS.toString(), ProductType.FRIDGE.toString(), R.drawable.whirlpool, 70000.00);

        Product blackDeckerVaccumCleaner = new Product("Black Decker", ProductCategory.ELECTRONICS.toString(), ProductType.VACCUM_CLEANER.toString(), R.drawable.black_decker, 50000.00);
        Product dyslonVaccumCleaner = new Product("Dyslon", ProductCategory.ELECTRONICS.toString(), ProductType.VACCUM_CLEANER.toString(), R.drawable.dyslon, 60000.00);
        Product eurekaVaccumCleaner = new Product("Eureka", ProductCategory.ELECTRONICS.toString(), ProductType.VACCUM_CLEANER.toString(), R.drawable.eureka, 70000.00);
        Product inalsaVaccumCleaner = new Product("Inasla", ProductCategory.ELECTRONICS.toString(), ProductType.VACCUM_CLEANER.toString(), R.drawable.inalsa, 80000.00);
        Product karcharVaccumCleaner = new Product("Karchar", ProductCategory.ELECTRONICS.toString(), ProductType.VACCUM_CLEANER.toString(), R.drawable.karcher, 90000.00);

        shoppingRepo.addProduct(macLaptop);
        shoppingRepo.addProduct(lenovaLaptop);
        shoppingRepo.addProduct(dellLaptop);
        shoppingRepo.addProduct(asusLaptop);
        shoppingRepo.addProduct(acerLaptop);

        shoppingRepo.addProduct(godrejFridge);
        shoppingRepo.addProduct(haireFridge);
        shoppingRepo.addProduct(lgFridge);
        shoppingRepo.addProduct(whirlpoolFridge);

        shoppingRepo.addProduct(blackDeckerVaccumCleaner);
        shoppingRepo.addProduct(dyslonVaccumCleaner);
        shoppingRepo.addProduct(eurekaVaccumCleaner);
        shoppingRepo.addProduct(inalsaVaccumCleaner);
        shoppingRepo.addProduct(karcharVaccumCleaner);
    }

    private void addFurnitureProducts() {
        Product celloChair = new Product("Cello", ProductCategory.FURNITURES.toString(), ProductType.CHAIR.toString(), R.drawable.cello, 6000.00);
        Product kingsChair = new Product("Kings", ProductCategory.FURNITURES.toString(), ProductType.CHAIR.toString(), R.drawable.kings, 5000.00);
        Product midBackChair = new Product("Mid Back", ProductCategory.FURNITURES.toString(), ProductType.CHAIR.toString(), R.drawable.midback, 8000.00);
        Product vitaChair = new Product("Vita", ProductCategory.FURNITURES.toString(), ProductType.CHAIR.toString(), R.drawable.vita, 7000.00);

        Product forzzaCot = new Product("Forzza", ProductCategory.FURNITURES.toString(), ProductType.COT.toString(), R.drawable.forzza, 12000.00);
        Product furniturekraftCot = new Product("Furniturekraft", ProductCategory.FURNITURES.toString(), ProductType.COT.toString(), R.drawable.furniturekraft, 15000.00);
        Product nilkamalCot = new Product("Nilkamal", ProductCategory.FURNITURES.toString(), ProductType.COT.toString(), R.drawable.nilkamal_cot, 18000.00);
        Product spacewoodCot = new Product("Spacewood", ProductCategory.FURNITURES.toString(), ProductType.COT.toString(), R.drawable.spacewood_cot, 20000.00);

        Product cabinetCupboard = new Product("Cabinet", ProductCategory.FURNITURES.toString(), ProductType.CUP_BOARD.toString(), R.drawable.cabinet, 25000.00);
        Product nilkamalCupboard = new Product("Nilkamal", ProductCategory.FURNITURES.toString(), ProductType.CUP_BOARD.toString(), R.drawable.nilkamal, 18000.00);
        Product spaceCupboard = new Product("Space", ProductCategory.FURNITURES.toString(), ProductType.CUP_BOARD.toString(), R.drawable.spacewood, 30000.00);
        Product supremeCupboard = new Product("Supreme", ProductCategory.FURNITURES.toString(), ProductType.CUP_BOARD.toString(), R.drawable.supreme, 20000.00);

        shoppingRepo.addProduct(celloChair);
        shoppingRepo.addProduct(kingsChair);
        shoppingRepo.addProduct(midBackChair);
        shoppingRepo.addProduct(vitaChair);

        shoppingRepo.addProduct(forzzaCot);
        shoppingRepo.addProduct(furniturekraftCot);
        shoppingRepo.addProduct(nilkamalCot);
        shoppingRepo.addProduct(spacewoodCot);

        shoppingRepo.addProduct(cabinetCupboard);
        shoppingRepo.addProduct(nilkamalCupboard);
        shoppingRepo.addProduct(spaceCupboard);
        shoppingRepo.addProduct(supremeCupboard);
    }

    private void setValues() {
        electronicProductsThumbnails = new ArrayList<>();
        furnitureProductsThumbnails = new ArrayList<>();

        final SharedPreference sharedPreference = ApplicationController.getAppInstance().getSharedPreference();
        List<ProductThumbnail> productThumbnails = sharedPreference.getProductThumbnails(this);
        if(productThumbnails == null) {
            addProductThumbNails();
            addElectronicProducts();
            addFurnitureProducts();

            shoppingRepo.getAllProductThumbnails().observe(this, new Observer<List<ProductThumbnail>>() {
                @Override
                public void onChanged(@Nullable List<ProductThumbnail> productThumbnails) {
                    sharedPreference.saveProductThumbnails(MainActivity.this, productThumbnails);
                    setProductThumbnailsToAdapter(productThumbnails);
                }
            });
        } else {
            setProductThumbnailsToAdapter(productThumbnails);
        }
    }

    private void setProductThumbnailsToAdapter(List<ProductThumbnail> productThumbnails) {
        for(ProductThumbnail productThumbnail : productThumbnails) {
            if(productThumbnail.getCategory().equalsIgnoreCase(ProductCategory.ELECTRONICS.toString())) {
                if(!electronicProductsThumbnails.contains(productThumbnail)) {
                    electronicProductsThumbnails.add(productThumbnail);
                }
            } else if(productThumbnail.getCategory().equalsIgnoreCase(ProductCategory.FURNITURES.toString())) {
                if(!furnitureProductsThumbnails.contains(productThumbnail)) {
                    furnitureProductsThumbnails.add(productThumbnail);
                }
            }
        }

        setAdapters();
    }

    private void setRecyclerViewAttributes() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewElectronics.setLayoutManager(linearLayoutManager1);
        mRecyclerViewElectronics.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewFurnitures.setLayoutManager(linearLayoutManager2);
        mRecyclerViewFurnitures.setItemAnimator(new DefaultItemAnimator());
    }

    private void setAdapters() {
        mAdapterElectronics = new ProductThumbnailRecyclerAdapter(electronicProductsThumbnails);
        mAdapterFurnitures = new ProductThumbnailRecyclerAdapter(furnitureProductsThumbnails);

        mRecyclerViewElectronics.setAdapter(mAdapterElectronics);
        mRecyclerViewFurnitures.setAdapter(mAdapterFurnitures);
    }

    @OnClick(R.id.cart_btn)
    public void onClick(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
