package org.psliwa.scalacheckworkshop.chapter_03

import org.psliwa.scalacheckworkshop.chapter_03.Graph.Graph
import org.psliwa.scalacheckworkshop.chapter_03.GraphGen.GraphAndStartingNode
import org.scalacheck.Shrink

object GraphShrink {
  private def graphShrink[Node]: Shrink[Graph[Node]] = Shrink { graph =>
    graph.keySet.headOption match {
      case Some(nodeToDrop) =>
        val filteredGraph = graph.map { case(node, neighbors) => node -> (neighbors - nodeToDrop) }
        Stream(filteredGraph - nodeToDrop)
      case None => Stream.empty
    }
  }

  implicit def graphAndStartingNodeShrink: Shrink[GraphAndStartingNode] = Shrink { case GraphAndStartingNode(graph, startingNode) =>
    graphShrink.shrink(graph).flatMap {
      case newGraph if newGraph.keySet.contains(startingNode) =>
        Stream(GraphAndStartingNode(newGraph, startingNode))
      case newGraph =>
        newGraph.keySet.headOption match {
          case Some(node) =>
            Stream(GraphAndStartingNode(graph, node) )
          case None => Stream.empty
        }
    }
  }
}
