import React from 'react'
import './App.css'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Home from './pages/Home'
import Self from './pages/Self'
import Recommend from './pages/Recommend'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/login' element={<Login />}></Route>
          <Route path='/home' element={<Home/>}>
            <Route path='Recommend' element={<Recommend/>}></Route>
            <Route path='my' element={<Self/>}></Route>
          </Route>
          <Route path='register' element={<Register/>}></Route>
          <Route path='*' element={<Navigate to='/login' />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
