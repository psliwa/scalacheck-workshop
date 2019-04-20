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

  // custom generators
  test("all paths from given node should contain the node") {
    forAll(graphAndStartingNodeGen) { graphAndSingleNode: GraphAndStartingNode =>
      val GraphAndStartingNode(graph, node) = graphAndSingleNode

      val paths = Graph.pathsFromNode(node, graph)

      val firstNodes = paths.flatMap(_.headOption)
      firstNodes.foreach(_ should be(node))
    }
  }

  test("there should be at least the same number of paths as neighbours") {
    forAll(graphAndStartingNodeGen) { graphAndSingleNode: GraphAndStartingNode =>
      val GraphAndStartingNode(graph, node) = graphAndSingleNode

      val paths = Graph.pathsFromNode(node, graph)

      paths.size should be >= graph.getOrElse(node, Set.empty).size
    }
  }

  test("all paths contain at least one neighbour") {
    forAll(graphAndStartingNodeGen) { graphAndSingleNode: GraphAndStartingNode =>
      val GraphAndStartingNode(graph, node) = graphAndSingleNode
      val paths = Graph.pathsFromNode(node, graph)
      val neighbours = findNeighbours(graph, node)

      paths.foreach(_ should contain atLeastOneElementOf neighbours)
    }
  }

  test("when path is longer than 2 that means any neighbours of neighbours must be in the path") {
    forAll(graphAndStartingNodeGen) { graphAndSingleNode: GraphAndStartingNode =>
      val GraphAndStartingNode(graph, node) = graphAndSingleNode
      val paths = Graph.pathsFromNode(node, graph)
      val neighbours = findNeighbours(graph, node)
      val neighboursOfNeighbours = neighbours.flatMap(findNeighbours(graph, _))

      val longPaths = paths.filter(_.size > 2)

      longPaths.foreach(_ should contain atLeastOneElementOf neighboursOfNeighbours)
    }
  }

  test("when graph is total there is a path with all nodes for given node") {
    forAll(totalGraphAndStartingNodeGen, SizeRange(6)) { graphAndSingleNode: GraphAndStartingNode =>
      val GraphAndStartingNode(graph, node) = graphAndSingleNode

      // topic: guardian
      whenever(graph.keys.size > 1) {
        val paths = Graph.pathsFromNode(node, graph)
        paths.find(path => path.toSet == graph.keySet) should not be empty
      }
    }
  }

}
