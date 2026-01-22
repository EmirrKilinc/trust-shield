import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AppProvider } from './context/AppContext';
import Layout from './components/common/Layout';
import Dashboard from './pages/Dashboard';
import Events from './pages/Events';
import RiskProfiles from './pages/RiskProfiles';
import RiskRules from './pages/RiskRules';
import FraudCases from './pages/FraudCases';
import Decisions from './pages/Decisions';
import Users from './pages/Users';

function App() {
  return (
    <AppProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<Dashboard />} />
            <Route path="events" element={<Events />} />
            <Route path="risk-profiles" element={<RiskProfiles />} />
            <Route path="risk-rules" element={<RiskRules />} />
            <Route path="fraud-cases" element={<FraudCases />} />
            <Route path="decisions" element={<Decisions />} />
            <Route path="users" element={<Users />} />
          </Route>
        </Routes>
      </Router>
    </AppProvider>
  );
}

export default App;