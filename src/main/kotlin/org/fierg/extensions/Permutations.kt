import java.util.*

internal class Permutations<E>(arr: Array<E>) :
    MutableIterator<Array<E>?> {
    private val arr: Array<E>
    private val ind: IntArray
    private var has_next: Boolean
    var output : Array<E>

    init {
        this.arr = arr.clone()
        ind = IntArray(arr.size)
        //convert an array of any elements into array of integers - first occurrence is used to enumerate
        val hm: MutableMap<E, Int> = HashMap()
        for (i in arr.indices) {
            var n = hm[arr[i]]
            if (n == null) {
                hm[arr[i]] = i
                n = i
            }
            ind[i] = n.toInt()
        }
        Arrays.sort(ind) //start with ascending sequence of integers


        //output = new E[arr.length]; <-- cannot do in Java with generics, so use reflection
        output = java.lang.reflect.Array.newInstance(arr.javaClass.componentType, arr.size) as Array<E>
        has_next = true
    }

    override fun hasNext(): Boolean {
        return has_next
    }

    /**
     * Computes next permutations. Same array instance is returned every time!
     * @return
     */
    override fun next(): Array<E> {
        if (!has_next) throw NoSuchElementException()
        for (i in ind.indices) {
            output[i] = arr[ind[i]]
        }


        //get next permutation
        has_next = false
        for (tail in ind.size - 1 downTo 1) {
            if (ind[tail - 1] < ind[tail]) { //still increasing

                //find last element which does not exceed ind[tail-1]
                var s = ind.size - 1
                while (ind[tail - 1] >= ind[s]) s--
                swap(ind, tail - 1, s)

                //reverse order of elements in the tail
                var i = tail
                var j = ind.size - 1
                while (i < j) {
                    swap(ind, i, j)
                    i++
                    j--
                }
                has_next = true
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