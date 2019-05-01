package org.psliwa.scalacheckworkshop.chapter_03

import org.psliwa.scalacheckworkshop.chapter_03.Graph.{Graph, Neighbors}
import org.psliwa.scalacheckworkshop.chapter_03.GraphGen._
import org.psliwa.scalacheckworkshop.chapter_03.GraphShrink.graphAndStartingNodeShrink
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSuite, Matchers}

class GraphSpec extends FunSuite with PropertyChecks with Matchers {

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfig(maxSize = 8)


  def findNeighbours(graph: Graph[Node], node: Node): Neighbors[Node] = {
    graph.getOrElse(node, Set.empty)
  }

  test("all paths from given node should start with the node") {
    forAll(graphAndStartingNodeGen) { graphAndSingleNode: GraphAndStartingNode =>
    }
  }

  test("when graph is total there is a path with all nodes for given node") {
    forAll(totalGraphAndStartingNodeGen, SizeRange(6)) { graphAndSingleNode: GraphAndStartingNode =>
    }
  }

}
