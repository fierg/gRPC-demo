package io.github.fierg.extensions

import java.util.*

internal class Permutations<E>(arr: Array<E>) :
    MutableIterator<Array<E>?> {
    private val arr: Array<E>
    private val ind: IntArray
    private var hasNext: Boolean
    var output : Array<E>

    init {
        this.arr = arr.clone()
        ind = IntArray(arr.size)
        val hm: MutableMap<E, Int> = HashMap()
        for (i in arr.indices) {
            var n = hm[arr[i]]
            if (n == null) {
                hm[arr[i]] = i
                n = i
            }
            ind[i] = n.toInt()
        }
        Arrays.sort(ind)
        output = java.lang.reflect.Array.newInstance(arr.javaClass.componentType, arr.size) as Array<E>
        hasNext = true
    }

    override fun hasNext(): Boolean {
        return hasNext
    }

    /**
     * Computes next permutations. Same array instance is returned every time!
     * @return
     */
    override fun next(): Array<E> {
        if (!hasNext) throw NoSuchElementException()
        for (i in ind.indices) {
            output[i] = arr[ind[i]]
        }

        hasNext = false
        for (tail in ind.size - 1 downTo 1) {
            if (ind[tail - 1] < ind[tail]) { //still increasing
                var s = ind.size - 1
                while (ind[tail - 1] >= ind[s]) s--
                swap(ind, tail - 1, s)
                var i = tail
                var j = ind.size - 1
                while (i < j) {
                    swap(ind, i, j)
                    i++
                    j--
                }
                hasNext = true
                break
            }
        }
        return output
    }

    private fun swap(arr: IntArray, i: Int, j: Int) {
        val t = arr[i]
        arr[i] = arr[j]
        arr[j] = t
    }

    override fun remove() {
        TODO("Not yet implemented")
    }
}