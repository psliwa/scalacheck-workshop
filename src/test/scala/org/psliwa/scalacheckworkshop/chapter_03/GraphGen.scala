package org.psliwa.scalacheckworkshop.chapter_03

import org.psliwa.scalacheckworkshop.chapter_03.Graph._
import org.scalacheck.Gen

object GraphGen {
  type Node = Int

  private val allNodesGen: Gen[Set[Node]] = ???

  private def someNeighborsCountGen: Gen[Int] = ???

  private def maxNeighboursCountGen: Gen[Int] = ???

  private def neighborsGen(neighborsCountGen: Gen[Int])(node: Node, allNodes: Set[Node]): Gen[Neighbors[Node]] = ???

  private def graphFromNodesGen(neighborsCount: Gen[Int])(nodes: Set[Node]): Gen[Graph[Node]] = {
    import scala.collection.JavaConverters._

    val leafsGen: Set[Gen[(Node, Neighbors[Node])]] = nodes.map(node => neighborsGen(neighborsCount)(node, nodes).map(neighbors => node -> neighbors))
    val graphGen = Gen.sequence(leafsGen)

    graphGen.map(_.asScala.toMap)
  }

  case class GraphAndStartingNode(graph: Graph[Node], node: Node)

  def graphAndStartingNodeGen: Gen[GraphAndStartingNode] = graphAndStartingNodeGen(someNeighborsCountGen)

  def totalGraphAndStartingNodeGen: Gen[GraphAndStartingNode] = graphAndStartingNodeGen(maxNeighboursCountGen)

  private def graphAndStartingNodeGen(neighborsCountGen: Gen[Int]): Gen[GraphAndStartingNode] = ???
}
