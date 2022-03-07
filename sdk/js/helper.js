export function longToByteArray(size, long) {
    var byteArray = new Array(size)

    for ( var index = 0; index < byteArray.length; index ++ ) {
        var byte = long & 0xff
        byteArray [ byteArray.length - index - 1 ] = byte
        long = (long - byte) / 256
    }

    return byteArray
}

export function hexToBytes(size, hex) {
    if (hex.startsWith('0x')) {
        hex = hex.substr(2, hex.length - 2)
    }
    var byteArray = new Array(size).fill(0)

    for (var index = 0; index < hex.length && index/2 < size; index += 2) {
        var byte = parseInt(hex.substr(hex.length-index-2, 2), 16)
        byteArray [byteArray.length - index/2 - 1 ] = byte
    }

    return byteArray
}