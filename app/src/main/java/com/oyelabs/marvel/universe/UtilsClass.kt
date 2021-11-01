package com.oyelabs.marvel.universe

object Utils{
    // these are constants we don't save data in this form but this is demo app so i am using it without any hesitation
    val ts =
        "1" // ts - a timestamp (or other long string which can change on a request-by-request basis)

    //    hash - a md5 digest of the ts parameter, your private key and your public key (e.g. md5(ts+privateKey+publicKey)
    // for now we generate this hashmap online and use it anywhere
    val hash = "e0ebba39cf6b666df2ebe2055496d6a1"
    val publicKey = "c21dfd2ef420f99a9ec317e8ce82995b"
    val CurrentOffSet = 0 // we increase it by 20
}