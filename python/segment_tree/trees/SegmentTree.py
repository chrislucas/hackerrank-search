from math import log2


class SegmentTree:

    def __init__(self, data):
        self.data = data
        self.len_data = len(data)
        self.height_tree = log2(len(data))
        self.mem_size = int(2 * 2 ** self.height_tree - 1)
        self.segment_tree = [0] * self.mem_size
        self.build_tree(0, self.len_data-1, 0)

    def get_middle(self, s, e):
        return (e - s) // 2 + s

    '''
        :param data = array para construir a segment tree
        :param s = indice que indica o inicio do array 'data'
        :param e = indice que indica o fim do array 'data'
        :param cur_index = indice da segment_tree. Usamos essa valor
        para definir qual o valor do n-esino nó da Segment Tree
    '''
    def build_tree(self, s, e, cur_index):
        if s == e:
            self.segment_tree[cur_index] = self.data[e]
            return self.data[e]
        middle_index = self.get_middle(s, e)
        '''
        usamos um array para representar a Segment Tree
        dado um indice i, _tree[i] eh o no raiz e _tree[i*2+1] eh o
        no da esquerda e _tree[i*2+2] o no da direita
        '''
        l_value = self.build_tree(s, middle_index, cur_index * 2 + 1)
        r_value = self.build_tree(middle_index + 1, e, cur_index *2 + 2)
        self.segment_tree[cur_index] = l_value + r_value
        return self.segment_tree[cur_index]


    '''
        :parameter index: indice que se quer atualizar no array 'data'
        :parameter value: valor para atualizar
    '''
    def update(self, index, value):
        if index < 0 or index > self.len_data - 1:
            return
        # valor que precisa ser adicionado aos elementos da arvore de segmento
        diff = self.data[index] - value
        self.data[index] = value
        return self._update(0, self.len_data - 1, index, diff, 0)

    def _update(self, li, ls, index, value, cur_index):
        if index < li or index > ls:
            return

        self.segment_tree[cur_index] += value
        if li != ls:
            middle_index = self.get_middle(li, ls)
            self._update(li, middle_index, index, value, cur_index * 2 + 1)
            self._update(middle_index+1, ls, index, value, cur_index * 2 + 2)

        return

    '''
        :parameter li = limite inferior da arvore (0)
        :parameter ls = limite superiro da arvore (n)
        : i e j sao os limites que se quer saber a soma
    '''
    def _prefix_sum(self, li, ls, i, j, cur_index):
        if i > ls or j < li or i > j:
            return 0
        elif i <= li and j >= ls:
            return self.segment_tree[cur_index]
        middle_index = self.get_middle(i, j)
        l_value = self._prefix_sum(li, middle_index, i, j, cur_index * 2 + 1)
        r_value = self._prefix_sum(middle_index + 1, ls, i, j, cur_index * 2 + 2)
        return l_value + r_value

    def prefix_sum(self, i, j):
        if i < 0 or j > self.len_data - 1:
            return 0
        return self._prefix_sum(0, self.len_data - 1, i, j, 0)


matrix = [
    [1, 3, 5, 7, 9, 11]
    , [1, 2, 3, 4, 5, 6, 7, 8]
]
idx = 0
st = SegmentTree(matrix[idx])

print(st.prefix_sum(0, 3))
print(st.prefix_sum(1, 3))
print(st.update(1, 10))
#print(st.update(0, 10))

if __name__ == '__main__':
    pass
