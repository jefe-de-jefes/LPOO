package com.gympos.app;

import java.util.ArrayList;
import java.util.List;

import com.gympos.model.Employee;
import com.gympos.service.AccessService;
import com.gympos.service.AuthService;
import com.gympos.service.BackupService;
import com.gympos.service.ClientService;
import com.gympos.service.InventoryService;
import com.gympos.service.MembershipService;
import com.gympos.service.PaymentService;
import com.gympos.service.ReportService;
import com.gympos.service.RewardService;
import com.gympos.service.ScheduleService;
import com.gympos.util.SerializationStore;
import com.gympos.util.Threading;

public final class AppContext {
    public static Employee activeUser;
    public static AuthService authService;
    public static ClientService clientService;
    public static MembershipService membershipService;
    public static PaymentService paymentService;
    public static ReportService reportService;
    public static AccessService accessService;
    public static InventoryService inventoryService;
    public static ScheduleService scheduleService;
    public static BackupService backupService;
    public static RewardService rewardService;

    public static void init() {
        SerializationStore.initBasePaths("./data");
        Threading.init();

        List<Employee> employees = SerializationStore.<List<Employee>>load("employees.dat");
        if (employees == null) {
            employees = new ArrayList<>();
            String adminPasswordHash = AuthService.hashPassword("123");
            employees.add(new Employee("1", "Admin", "admin", adminPasswordHash));
            SerializationStore.save("employees.dat", employees);
            SerializationStore.save("employees.dat", employees);
        }
        authService = new AuthService(employees);

        clientService = new ClientService();
        membershipService = new MembershipService();
        paymentService = new PaymentService();
        reportService = new ReportService();
        accessService = new AccessService();
        inventoryService = new InventoryService();
        scheduleService = new ScheduleService();
        backupService = new BackupService();
        rewardService = new RewardService();

        backupService.scheduleNightlyBackup();
        membershipService.scheduleExpiringNotifications();
    }

    public static void shutdown() {
        Threading.shutdown();
    }
}
