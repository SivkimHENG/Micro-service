import './App.css'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import { Login as AuthenticatePage } from './components/Login'

function App() {

    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/login" element={<AuthenticatePage />} />
                </Routes>
            </div>


        </Router>
    )
}

export default App
