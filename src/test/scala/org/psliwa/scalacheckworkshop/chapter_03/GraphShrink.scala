package org.psliwa.scalacheckworkshop.chapter_03

import org.psliwa.scalacheckworkshop.chapter_03.Graph.Graph
import org.psliwa.scalacheckworkshop.chapter_03.GraphGen.GraphAndStartingNode
import org.scalacheck.Shrink

object GraphShrink {
  private def graphShrink[Node]: Shrink[Graph[Node]] = Shrink { _ => Stream.empty }

  implicit def graphAndStartingNodeShrink: Shrink[GraphAndStartingNode] = Shrink {  _ => Stream.empty }
}
