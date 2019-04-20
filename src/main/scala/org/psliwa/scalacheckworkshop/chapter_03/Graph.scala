package org.psliwa.scalacheckworkshop.chapter_03

object Graph {
  type Graph[Node] = Map[Node, Neighbors[Node]]
  type Neighbors[Node] = Set[Node]
  type GraphPath[Node] = List[Node]

  def pathsFromNode[Node](node: Node,
                          graph: Graph[Node]): Set[GraphPath[Node]] = {
    def loop(node: Node, currentPath: GraphPath[Node], visited: Set[Node]): Set[GraphPath[Node]] = {
      val updatedCurrentPath = currentPath :+ node // CORRECT 1
//      val updatedCurrentPath = currentPath :+ node :+ node // BUG 1
      val toReturn = if (updatedCurrentPath.size >= 2) Set(updatedCurrentPath) else Set.empty[GraphPath[Node]]
      if (visited.contains(node)) {
        toReturn
//      } else if(visited.size > 2) { // BUG 2
//        toReturn
      } else {
        graph.get(node) match {
          case None => toReturn
          case Some(neighbours) => toReturn ++ neighbours.flatMap { neighbour =>
            loop(neighbour, updatedCurrentPath, visited + node)
          }
        }
      }
    }

    loop(node, List.empty, Set.empty)
  }

  def map[Node, NewNode](graph: Graph[Node])(f: Node => NewNode): Graph[NewNode] = {
    graph.map { case(node, neighbors) => f(node) -> neighbors.map(f) }
  }

}
