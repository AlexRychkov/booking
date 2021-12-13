package org.booking

class DeviceInUse() : RuntimeException("Device already in use")

class BookedBySomeoneElse() : RuntimeException("Device was booked by someone else, ask the person to return it")