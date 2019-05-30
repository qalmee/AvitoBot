package ru.test.avito.pipeline;

public enum PipeState {
    None,
    Start,
    Buyer,
    Seller,
    //        buyerBranch
    SearchAdverts,
    SeeAdverts,
    //        sellerBranch
    CheckOwnAdverts,
    CreateAdvert,
    AddPhotosToAdvert,
    EditAdvert,
    EditAdvertPhotosStart,
    EditAdvertPhotos
}
