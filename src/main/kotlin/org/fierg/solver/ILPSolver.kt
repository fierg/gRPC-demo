package org.fierg.solver

import gurobi.*
import org.fierg.logger.Logger
import org.fierg.model.EncryptedGameInstance
import org.fierg.model.GameInstance
import org.fierg.model.Symbol
import kotlin.math.pow

class ILPSolver {

    fun solve(game: GameInstance): Pair<Double, String> {
        var index = 0
        val map = mutableMapOf<Int, Int>()
        game.numbers.forEach { array ->
            array.forEach { number ->
                if (number != null) {
                    map[index] = number
                }
                index++
            }
        }
        return solve(game.nrOfSymbols, map)
    }

    fun solve(game: EncryptedGameInstance): Pair<Double, String> {
        return solve(game.nrOfSymbols, mutableMapOf(), game.symbols)
    }


    private fun solve(
        nrOfSymbols: Int = 3,
        fixedVars: Map<Int, Int>,
        fixedSymbols: Map<Int, Array<Symbol?>>? = null
    ): Pair<Double, String> {
        val env = GRBEnv("pco-ilp.log")
        env.set(GRB.IntParam.LogToConsole, 0)
        env.start()
        // Create empty model
        val model = GRBModel(env)

        try {
            // Create variables
            val vars = mutableMapOf<Int, GRBVar>()
            addVariables(model, vars, fixedVars, nrOfSymbols)
            val terms = addConstraint(vars, model, fixedVars, fixedSymbols)
            addObjectiveFunction(vars, model, fixedVars)

            // Optimize model
            Logger.info("Solving instance with ILP (using ${vars.size} vars and $terms terms) ...")
            model.optimize()

            Logger.info("Obj : " + model.get(GRB.DoubleAttr.ObjVal))
            val resultVal = model.get(GRB.DoubleAttr.ObjVal)

            val resultString = backTrackSolution(vars, fixedVars)

            // Dispose of model and environment
            model.dispose()
            env.dispose()

            return Pair(resultVal, resultString)

        } catch (e: GRBException) {
            Logger.error(" Error code : " + e.errorCode.toString() + ". " + e.message)
            if (model.get(GRB.IntAttr.Status) == 3) {
                Logger.error("Model status = 3 -> model INFEASIBLE")
            }

            return Pair(-1.0, "")
        }
    }

    private fun backTrackSolution(
        vars: MutableMap<Int, GRBVar>,
        fixedVars: Map<Int, Int>
    ): String {
        val sb = StringBuilder()

        for (position in 0..8) {
            if (fixedVars.containsKey(position)) {
                Logger.debug("position $position is fixed ${fixedVars[position]}")
                sb.append(fixedVars[position])
            } else {
                Logger.debug("position $position is ${vars[position]!!.get(GRB.DoubleAttr.X)}")
                sb.append(vars[position]!!.get(GRB.DoubleAttr.X).toInt())
            }

            when (position % 3) {
                0 -> sb.append(" + ")
                1 -> sb.append(" = ")
                2 -> sb.append("\n")
            }
        }
        println(sb.toString())
        return sb.toString()
    }

    private fun addObjectiveFunction(
        vars: MutableMap<Int, GRBVar>,
        model: GRBModel,
        fixedVars: Map<Int, Int>
    ) {
        val expr = GRBLinExpr()

        addTermsToExpression(fixedVars, vars, expr, 0, 1, 2)
        addTermsToExpression(fixedVars, vars, expr, 3, 4, 5)
        addTermsToExpression(fixedVars, vars, expr, 6, 7, 8)
        addTermsToExpression(fixedVars, vars, expr, 0, 3, 6)
        addTermsToExpression(fixedVars, vars, expr, 1, 4, 7)
        addTermsToExpression(fixedVars, vars, expr, 2, 5, 8)

        model.setObjective(expr, GRB.MINIMIZE)
    }


    private fun addSumConstraint(
        vars: Map<Int, GRBVar>,
        a: Int,
        b: Int,
        c: Int,
        model: GRBModel,
        fixedVars: Map<Int, Int>
    ) {
        val expr = GRBLinExpr()
        addTermsToExpression(fixedVars, vars, expr, a, b, c)
        model.addConstr(expr, GRB.EQUAL, 0.0, "c$a$b$c")
    }

    private fun addTermsToExpression(
        fixedVars: Map<Int, Int>,
        vars: Map<Int, GRBVar>,
        expr: GRBLinExpr,
        a: Int,
        b: Int,
        c: Int
    ) {
        if (fixedVars.containsKey(a)) {
            expr.addConstant(fixedVars[a]!!.toDouble())
        } else {
            expr.addTerm(1.0, vars[a])
        }

        if (fixedVars.containsKey(b)) {
            expr.addConstant(fixedVars[b]!!.toDouble())
        } else {
            expr.addTerm(1.0, vars[b])
        }

        if (fixedVars.containsKey(c)) {
            expr.addConstant(-fixedVars[c]!!.toDouble())
        } else {
            expr.addTerm(-1.0, vars[c])
        }
    }

    private fun addConstraint(
        vars: Map<Int, GRBVar>,
        model: GRBModel,
        fixedVars: Map<Int, Int>,
        fixedSybols: Map<Int, Array<Symbol?>>?
    ): Int {

        addSumConstraint(vars, 0, 1, 2, model, fixedVars)
        addSumConstraint(vars, 3, 4, 5, model, fixedVars)
        addSumConstraint(vars, 6, 7, 8, model, fixedVars)
        addSumConstraint(vars, 0, 3, 6, model, fixedVars)
        addSumConstraint(vars, 1, 4, 7, model, fixedVars)
        addSumConstraint(vars, 2, 5, 8, model, fixedVars)

        val symbolMap = mutableMapOf<Symbol, MutableList<Pair<Int, Int>>>()
        if (!fixedSybols.isNullOrEmpty()) {
            fixedSybols.forEach { (index1, array) ->
                array.forEachIndexed { index2, symbol ->
                    if (symbol != null) {
                        if (symbolMap[symbol].isNullOrEmpty()) symbolMap[symbol] = mutableListOf()
                        symbolMap[symbol]!!.add(Pair(index1,index2))
                    }
                }
            }
        }

        return 6
    }

    private fun addVariables(
        model: GRBModel,
        vars: MutableMap<Int, GRBVar>,
        fixedVars: Map<Int, Int>,
        nrOfSymbols: Int
    ) {
        for (position in 0..8) {
            if (!fixedVars.containsKey(position)) {
                val variable = model.addVar(0.0, 10.0.pow(nrOfSymbols), 0.0, GRB.INTEGER, "x${position}")
                vars[position] = variable
            }
        }
    }
}