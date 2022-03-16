export function longToByteArray(size, long) {
    let byteArray = new Array(size)

    for ( let index = 0; index < byteArray.length; index ++ ) {
        let byte = long & 0xff
        byteArray [ byteArray.length - index - 1 ] = byte
        long = (long - byte) / 256
    }

    return byteArray
}

export function hexToBytes(size, hex) {
    if (hex.startsWith('0x')) {
        hex = hex.substr(2, hex.length - 2)
    }
    let byteArray = new Array(size).fill(0)

    for (let index = 0; index < hex.length && index/2 < size; index += 2) {
        let byte = parseInt(hex.substr(hex.length-index-2, 2), 16)
        byteArray [byteArray.length - index/2 - 1 ] = byte
    }

    return byteArray
}
