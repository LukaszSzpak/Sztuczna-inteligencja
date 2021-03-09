import unittest

from Cwiczenie_1.direction import Direction
from Cwiczenie_1.segment import Segment


class UnitTests(unittest.TestCase):

    def test_segment_add(self):
        seg1 = Segment(Direction.up, 1)
        seg1.add_length(Segment(Direction.up, 2))

        self.assertEqual(seg1.length, 3)
