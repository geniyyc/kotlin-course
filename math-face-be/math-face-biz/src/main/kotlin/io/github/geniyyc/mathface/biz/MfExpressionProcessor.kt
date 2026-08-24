package io.github.geniyyc.mathface.biz

import io.github.geniyyc.mathface.biz.general.initStatus
import io.github.geniyyc.mathface.biz.general.operation
import io.github.geniyyc.mathface.biz.general.stubs
import io.github.geniyyc.mathface.biz.repo.generateExpressionsIfEmpty
import io.github.geniyyc.mathface.biz.repo.initRepo
import io.github.geniyyc.mathface.biz.repo.prepareResult
import io.github.geniyyc.mathface.biz.repo.prepareSubmit
import io.github.geniyyc.mathface.biz.repo.repoCreateExpressions
import io.github.geniyyc.mathface.biz.repo.repoRead
import io.github.geniyyc.mathface.biz.repo.repoSearch
import io.github.geniyyc.mathface.biz.stubs.stubDbError
import io.github.geniyyc.mathface.biz.stubs.stubGenerateSuccess
import io.github.geniyyc.mathface.biz.stubs.stubNoCase
import io.github.geniyyc.mathface.biz.stubs.stubNotFound
import io.github.geniyyc.mathface.biz.stubs.stubSubmitSuccess
import io.github.geniyyc.mathface.biz.stubs.stubValidationBadId
import io.github.geniyyc.mathface.biz.stubs.stubValidationBadLevel
import io.github.geniyyc.mathface.biz.stubs.stubValidationBadValue
import io.github.geniyyc.mathface.biz.validation.finishExpressionFilterValidation
import io.github.geniyyc.mathface.biz.validation.finishSubmitValidation
import io.github.geniyyc.mathface.biz.validation.validateAnswerNotEmpty
import io.github.geniyyc.mathface.biz.validation.validateExpressionIdNotEmpty
import io.github.geniyyc.mathface.biz.validation.validateExpressionIdProperFormat
import io.github.geniyyc.mathface.biz.validation.validateLevelNotEmpty
import io.github.geniyyc.mathface.biz.validation.validateLevelProperRange
import io.github.geniyyc.mathface.biz.validation.validation
import io.github.geniyyc.mathface.common.MfContext
import io.github.geniyyc.mathface.common.MfCorSettings
import io.github.geniyyc.mathface.common.models.MfCommand
import io.github.geniyyc.mathface.cor.chain
import io.github.geniyyc.mathface.cor.rootChain
import io.github.geniyyc.mathface.cor.worker

class MfExpressionProcessor(private val corSettings: MfCorSettings = MfCorSettings.NONE) {
    suspend fun exec(ctx: MfContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<MfContext> {
        initStatus("Инициализация статуса")
        initRepo("Инициализация репозитория")

        operation("Генерация примеров", MfCommand.GENERATE) {
            stubs("Обработка стабов") {
                stubGenerateSuccess("Имитация успешной генерации")
                stubValidationBadLevel("Имитация ошибки уровня")
                stubValidationBadId("Имитация ошибки id")
                stubValidationBadValue("Имитация ошибки значения")
                stubNotFound("Имитация отсутствия выражения")
                stubDbError("Имитация ошибки базы данных")
                stubNoCase("Неверный stub-case")
            }
            validation("Валидация параметров генерации") {
                worker("Копируем поля в объект валидации") {
                    expressionFilterValidating = expressionFilterRequest.copy()
                }
                validateLevelNotEmpty("Уровень должен быть задан")
                validateLevelProperRange("Уровень должен быть в диапазоне 1..10")
                finishExpressionFilterValidation("Завершение проверок")
            }
            chain {
                title = "Логика поиска и сохранения"
                repoSearch("Поиск выражений в БД")
                generateExpressionsIfEmpty("Генерация примеров при отсутствии в БД")
                repoCreateExpressions("Сохранение сгенерированных выражений в БД")
            }
            prepareResult("Подготовка ответа")
        }

        operation("Отправка ответа", MfCommand.SUBMIT) {
            stubs("Обработка стабов") {
                stubSubmitSuccess("Имитация успешной проверки")
                stubValidationBadId("Имитация ошибки id")
                stubValidationBadValue("Имитация ошибки значения")
                stubNotFound("Имитация отсутствия выражения")
                stubDbError("Имитация ошибки базы данных")
                stubNoCase("Неверный stub-case")
            }
            validation("Валидация ответа") {
                worker("Копируем поля в объект валидации") {
                    submitValidating = submitRequest.copy()
                }
                validateExpressionIdNotEmpty("Идентификатор выражения должен быть задан")
                validateExpressionIdProperFormat("Идентификатор выражения должен иметь корректный формат")
                validateAnswerNotEmpty("Ответ должен быть задан")
                finishSubmitValidation("Завершение проверок")
            }
            chain {
                title = "Логика чтения и проверки"
                prepareSubmit("Подготовка объекта для чтения")
                repoRead("Чтение выражения из БД")
            }
            prepareResult("Подготовка ответа")
        }
    }.build()
}
