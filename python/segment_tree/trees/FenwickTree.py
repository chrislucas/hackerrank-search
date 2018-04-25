'''
https://en.wikipedia.org/wiki/Fenwick_tree
https://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
https://www.hackerearth.com/practice/notes/binary-indexed-tree-or-fenwick-tree/
'''

from math import log10

'''
Constatacoes interessantes
para n sendo uma  potencia de 2, bit[n] guarda a soma de  bit[1] + ... bit[n]
para n impar, a soma esta em bit[n]

Para generarlizar bit[i] guarda o somatorio dos elementos
entre i e i - (i << rms(i)) + 1 onde rms e a funcao que retorna
o bit significativo mais a direita.

exemplo rms(12) = 3 porque 12 = 1100b
12 - (1 << 3) + 1 = 5
'''


class BinaryIndexedTree:
    def __init__(self, data):
        self.data = data
        self.size_data = len(data)
        self.bit = [0] * (self.size_data + 1)
        self._build_tree()
        return

    def _build_tree(self):
        for i in range(self.size_data):
            self._update(i, self.bit[i])
        return

    # removing de last set bit from value
    # value - (value & (-value))
    def _update(self, cur_index, val):
        i = cur_index + 1
        while i <= self.size_data:
            self.bit[i] += val
            i = i + (i & (-i))

    def prefix_sum(self, i):
        acc = 0
        i += 1
        while i > 0:
            acc += self.bit[i]
            i = i - (i & (-i))
        return acc


def test_bit():
    # ~val -> complemento de 1
    '''
    print(~val)
    print(val & (~val))
    print(val - (val & (~val)))
    '''
    for val in range(0, 100):
        # val & (-val) retorna o valor (inteiro) do ultimo bit  significativo
        # exemplo val = 10, (10 and -10) => -10 0101+1=0111 entao 1010 and 0111 = 0010 = 2

        # -val = em binario o computador usa o complemento de 2 => 9 = 1001 , -9 = 0110 + 1 = 0111
        # exemplo 10 = 1010 = (1010 & 0111) = (0010)
        # exemplo 9 = 1001 = (1001 & 0111) = (0010)

        # val - (val & (-val)) retorna um valor 'n' (inteiro) apos remover o ultimo bit siginificativo de 'val'
        print(val, val & (-val), val - (val & (-val)), val + (val & (-val)))


def is_power_of2(n):
    return n & (n - 1) == 0


def rms_bit(val):
    return int(log10(val & (-val)) / log10(2) + 1)


def test_rms_bit():
    for x in range(1, 129):
        print(x, rms_bit(x))


#test_bit()

matrix = [
    [1, 2, 3, 4, 5, 6, 7, 8, 9]
    , [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
]
index = 0
ft = BinaryIndexedTree(matrix[index])

print(ft.prefix_sum(2))

if __name__ == '__main__':
    pass
