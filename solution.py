def process_converter(max_idx, converter):
    prev_node = -1
    current_node = max_idx
    # The subtree is how many elements we have in total for a node
    subtree = max_idx

    if current_node == converter:
        return prev_node

    prev_node = current_node

    while subtree > 1:
        # As we go down through the tree, each level reduces the number by half, which is the same as shifting 1 bit
        subtree = subtree >> 1

        left = current_node - subtree - 1
        right = current_node - 1

        if converter == left or converter == right:
            return prev_node

        if converter < left:
            current_node = left
        elif converter > left:
            current_node = right

        prev_node = current_node

    return -1


def solution(h, q):
    max_idx = 2**h-1
    print(max_idx)
    test = [process_converter(max_idx, converter) for converter in q]
    print(test)


if __name__ == "__main__":
    solution(5, [19, 14, 28])
