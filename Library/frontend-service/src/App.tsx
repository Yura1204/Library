import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CatalogComponent from './components/CatalogComponent';
import BookDetails from './components/BookDetails';
import AuthorDetails from './components/AuthorDetails';
import AddBookForm from './components/AddBookForm';
import AuthorForm from './components/AddAuthorForm';
import DeleteBookForm from './components/DeleteBookForm';
import DeleteAuthorForm from './components/DeleteAuthorFrom';

const App: React.FC = () => {
  return (
    <Router>
      <div>
        <h1>BookLibrary</h1>
        <Routes>
          <Route path='/' Component={CatalogComponent}/>
          <Route path='/books/:id' Component={BookDetails}/>
          <Route path="/author/:id" Component={AuthorDetails} />
          <Route path='/books/add' Component={AddBookForm}/>
          <Route path='/books/delete' Component={DeleteBookForm}/>
          <Route path='/authors/add' Component={AuthorForm}/>
          <Route path='authors/delete' Component={DeleteAuthorForm}/>
        </Routes>
      </div>
    </Router>
  );
};

export default App;