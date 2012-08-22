package search.managers

import java.io.File

import scala.Array.canBuildFrom

import search.documents.Document
import search.indexing.InvertedIndex
import search.indexing.SearchRanker
import search.parsing.Parser
import search.parsing.Parser.string2Iterator

object SearchManager {
  def apply(folder: File):SearchManager = {
    val x = new SearchManager()
    x.addFolderToIndex(folder)
    x
  }
}

case class SearchManager {

  private val _index: InvertedIndex = new InvertedIndex()
  private val ranker: SearchRanker = new SearchRanker(index)
  private val documentManager = new DocumentManager()

  def SearchManager(folder: File) = {
   this.addFolderToIndex(folder)
 }

  def x(folder: File) = {
    this.addFolderToIndex(folder)
  }
  
  def addFileToIndex(filename: String): Document = {
    addFileToIndex(new File(filename))
  }

  def addFileToIndex(file: File): Document = {
    val document = documentManager.parseFile(file)
    doesDocumentAlreadyExist(document) match {
      case Some(docFound) => docFound
      case None => {
        _index.addDocumentToIndex(document)
        document
      }
    }
  }

  def addFolderToIndex(folder: File): List[Document] = {
    val files = folder.listFiles()
    files.map(addFileToIndex(_)).toList
  }

  def doesDocumentAlreadyExist(document: Document): Option[Document] = {
    index.getAllDocuments.find(d => d.name == document.name)
  }

  def query(input: String) = {
    val queryable = documentManager.parseText(input)
    ranker.query(queryable).filter(d => d.score > 0.0)
  }
  
  def index = _index

}