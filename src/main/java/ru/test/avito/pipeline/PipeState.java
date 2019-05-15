package ru.test.avito.pipeline;

public enum PipeState {
    None,
    Start,
    Buyer,
    Seller,
    //    buyerBranch
    ShowAdverts,
    //    sellerBranch
    CheckOwnAdverts,
    CreateAdvert
}
