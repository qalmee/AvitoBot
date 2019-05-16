package ru.test.avito.pipeline;

public enum PipeState {
    None,
    Start,
    Buyer,
    Seller,
    //        buyerBranch
    SeeAdverts,
    //        sellerBranch
    CheckOwnAdverts,
    CreateAdvert,
    AddPhotosToAdvert
}
