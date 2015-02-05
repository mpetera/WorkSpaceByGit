package com.tradeshift.groovy

/**
 * Test the groovy code in the class
 */
class GroovyTest {
	public static void main(def args) {
		def list = []
		list = [1,2,3,4,5]
		println list[2]
		println list[-2]
		println list[1..3]
		println list + [6,7]
		println list - [2,5,7]
		println list << 6
		println list << [6,7]
	}
}
